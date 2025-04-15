package org.defix.chat.service.creator;

import org.defix.chat.api.dto.response.ChatKeysCreationDetails;
import org.defix.chat.service.EncryptionService;
import org.defix.chat.util.ChatCryptUtils;

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
