package com.robot.mapconverter.client;

import com.robot.mapconverter.dto.MapUploadMessage;
import com.robot.mapconverter.service.MapProcessingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MapUploadListener {
    private final MapProcessingService mapProcessingService;

    public MapUploadListener(MapProcessingService mapProcessingService) {
        this.mapProcessingService = mapProcessingService;
    }

    @KafkaListener(topics = "${app.kafka.map-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void handle(MapUploadMessage message) {
        mapProcessingService.process(message);
    }
}
