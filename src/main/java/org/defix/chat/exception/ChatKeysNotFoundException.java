package org.defix.chat.exception;

public class ChatKeysNotFoundException extends RuntimeException {
    public ChatKeysNotFoundException() {
        super("Chat keys not found.");
    }
}
