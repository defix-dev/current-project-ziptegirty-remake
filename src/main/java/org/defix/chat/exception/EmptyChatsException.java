package org.defix.chat.exception;

public class EmptyChatsException extends RuntimeException {
    public EmptyChatsException() {
        super("Empty chat.");
    }
}
