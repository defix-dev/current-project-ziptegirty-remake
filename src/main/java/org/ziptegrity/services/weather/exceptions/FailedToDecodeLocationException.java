package org.ziptegrity.services.weather.exceptions;

public class FailedToDecodeLocationException extends RuntimeException {
    public FailedToDecodeLocationException() {
        super("Failed to decode location.");
    }
}
