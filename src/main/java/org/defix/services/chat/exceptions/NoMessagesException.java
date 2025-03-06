package org.defix.services.chat.exceptions;

public class NoMessagesException extends RuntimeException {
    public NoMessagesException() {
        super("No messages.");
    }
}
