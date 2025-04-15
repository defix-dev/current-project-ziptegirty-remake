package org.defix.weather.exception;

public class FailedToDecodeLocationException extends RuntimeException {
    public FailedToDecodeLocationException() {
        super("Failed to decode location.");
    }
}
