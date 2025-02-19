package org.ziptegrity.services.chat.exceptions;

public class EmptyChatException extends RuntimeException {
    public EmptyChatException() {
        super("Empty chat.");
    }
}
