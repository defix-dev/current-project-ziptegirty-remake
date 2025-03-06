package org.defix.services.chat.exceptions;

public class ChatNotFoundException extends RuntimeException{
    public ChatNotFoundException() {
        super("Chat not found.");
    }
}
