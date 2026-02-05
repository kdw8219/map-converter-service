package com.robot.mapconverter.service;

import com.robot.mapconverter.dto.MapUploadMessage;
import com.robot.mapconverter.exception.MapProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.regex.Pattern;

@Service
public class MapProcessingService {
    private static final Pattern UNSAFE_CHARS = Pattern.compile("[^a-zA-Z0-9._-]");
    private final Path outputDir;

    public MapProcessingService(@Value("${app.map.output-dir:./maps}") String outputDir) {
        this.outputDir = Path.of(outputDir);
        try {
            Files.createDirectories(this.outputDir);
        } catch (IOException e) {
            throw new MapProcessingException("Failed to create output directory: " + this.outputDir, e);
        }
    }

    public void process(MapUploadMessage message) {
        int width = message.getWidth();
        int height = message.getHeight();
        if (width <= 0 || height <= 0) {
            throw new MapProcessingException("Invalid map dimensions: " + width + "x" + height);
        }

        byte[] grid;
        try {
            grid = Base64.getDecoder().decode(message.getDataB64());
        } catch (IllegalArgumentException e) {
            throw new MapProcessingException("Invalid base64 map payload", e);
        }

        long expectedSizeLong = (long) width * height;
        if (expectedSizeLong > Integer.MAX_VALUE) {
            throw new MapProcessingException("Map dimensions are too large: " + width + "x" + height);
        }
        int expectedSize = (int) expectedSizeLong;
        if (grid.length != expectedSize) {
            throw new MapProcessingException(
                    "Map payload size mismatch: expected " + expectedSize + ", got " + grid.length
            );
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        for (int i = 0; i < expectedSize; i++) {
            int value = grid[i];
            int gray;
            if (value == -1) {
                gray = 205;
            } else if (value <= 0) {
                gray = 255;
            } else if (value >= 100) {
                gray = 0;
            } else {
                gray = 255 - ((value * 255) / 100);
            }
            pixels[i] = (byte) gray;
        }

        String robotId = sanitize(message.getRobotId());
        long timestamp = message.getTimestampMs() > 0 ? message.getTimestampMs() : System.currentTimeMillis();
        Path output = outputDir.resolve(robotId + "_" + timestamp + ".png");

        try {
            ImageIO.write(image, "png", output.toFile());
        } catch (IOException e) {
            throw new MapProcessingException("Failed to store map image: " + output, e);
        }
    }

    private String sanitize(String robotId) {
        if (robotId == null || robotId.isBlank()) {
            return "unknown-robot";
        }
        return UNSAFE_CHARS.matcher(robotId).replaceAll("_");
    }
}
