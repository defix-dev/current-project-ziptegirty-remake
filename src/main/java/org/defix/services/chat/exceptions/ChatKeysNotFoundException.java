package org.defix.services.chat.exceptions;

public class ChatKeysNotFoundException extends RuntimeException {
    public ChatKeysNotFoundException() {
        super("Chat keys not found.");
    }
}
