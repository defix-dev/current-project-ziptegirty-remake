package org.defix.chat.service.provider;

import org.defix.chat.service.EncryptionService;
import org.defix.chat.util.ChatCryptUtils;

public class DecryptedPrivateKeyProvider implements Provider<String, Integer> {
    private final EncryptionService encryptionService;

    public DecryptedPrivateKeyProvider(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public String provide(Integer userId) {
        return ChatCryptUtils.decrypt(encryptionService.getPrivateKey(userId));
    }
}
