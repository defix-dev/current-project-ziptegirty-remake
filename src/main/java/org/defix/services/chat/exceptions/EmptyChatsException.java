package org.defix.services.chat.exceptions;

public class EmptyChatsException extends RuntimeException {
    public EmptyChatsException() {
        super("Empty chat.");
    }
}
