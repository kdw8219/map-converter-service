package com.robot.mapconverter.dto;

public class MapUploadMessage {
    private String robotId;
    private int width;
    private int height;
    private double resolution;
    private double originX;
    private double originY;
    private double originZ;
    private double originW;
    private String dataB64;
    private long timestampMs;
    private String frameId;
    private String timestamp;

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getResolution() {
        return resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }

    public double getOriginX() {
        return originX;
    }

    public void setOriginX(double originX) {
        this.originX = originX;
    }

    public double getOriginY() {
        return originY;
    }

    public void setOriginY(double originY) {
        this.originY = originY;
    }

    public double getOriginZ() {
        return originZ;
    }

    public void setOriginZ(double originZ) {
        this.originZ = originZ;
    }

    public double getOriginW() {
        return originW;
    }

    public void setOriginW(double originW) {
        this.originW = originW;
    }

    public String getDataB64() {
        return dataB64;
    }

    public void setDataB64(String dataB64) {
        this.dataB64 = dataB64;
    }

    public long getTimestampMs() {
        return timestampMs;
    }

    public void setTimestampMs(long timestampMs) {
        this.timestampMs = timestampMs;
    }

    public String getFrameId() {
        return frameId;
    }

    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
