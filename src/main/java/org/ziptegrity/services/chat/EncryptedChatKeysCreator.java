package org.ziptegrity.services.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ziptegrity.services.chat.abstractions.Creator;
import org.ziptegrity.services.chat.objects.ChatKeysApiDTO;
import org.ziptegrity.services.chat.objects.ChatKeysCreationDetails;

import java.util.Base64;

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
