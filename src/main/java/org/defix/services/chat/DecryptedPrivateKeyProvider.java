package org.defix.services.chat;

import org.defix.services.chat.abstractions.Provider;

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
