package org.ziptegrity.services.chat.exceptions;

public class ChatKeysAlreadyCreatedException extends RuntimeException {
    public ChatKeysAlreadyCreatedException() {
        super("Chat keys already created.");
    }
}
