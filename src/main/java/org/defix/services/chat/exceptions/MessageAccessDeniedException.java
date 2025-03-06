package org.defix.services.chat.exceptions;

public class MessageAccessDeniedException extends RuntimeException{
    public MessageAccessDeniedException() {
        super("Message access denied.");
    }
}
