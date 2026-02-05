package com.robot.mapconverter.exception;

public class MapProcessingException extends RuntimeException {
    public MapProcessingException(String message) {
        super(message);
    }

    public MapProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
