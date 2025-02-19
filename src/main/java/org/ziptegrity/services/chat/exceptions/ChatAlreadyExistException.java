package org.ziptegrity.services.chat.exceptions;

public class ChatAlreadyExistException extends RuntimeException{
    public ChatAlreadyExistException() {
        super("Chat already exist.");
    }
}
