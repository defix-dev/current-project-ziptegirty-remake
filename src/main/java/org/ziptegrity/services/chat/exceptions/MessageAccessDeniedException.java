package org.ziptegrity.services.chat.exceptions;

public class MessageAccessDeniedException extends RuntimeException{
    public MessageAccessDeniedException() {
        super("Message access denied.");
    }
}
