package org.defix.services.chat;

import org.defix.services.chat.abstractions.Creator;
import org.defix.services.chat.objects.ChatKeysCreationDetails;

public class EncryptedChatKeysCreator implements Creator<ChatKeysCreationDetails> {
    private final EncryptionService encryptionService;

    public EncryptedChatKeysCreator(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public void create(ChatKeysCreationDetails details) {
        encryptionService.createKeys(
                details.getUserId(),
                ChatCryptUtils.encrypt(details.getPrivateKey()),
                ChatCryptUtils.encrypt(details.getPublicKey())
        );
    }
}
