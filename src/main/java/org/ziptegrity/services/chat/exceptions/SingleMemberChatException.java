package org.ziptegrity.services.chat.exceptions;

public class SingleMemberChatException extends RuntimeException {
    public SingleMemberChatException() {
        super("Single member chat.");
    }
}
