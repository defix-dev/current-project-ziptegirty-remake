package org.defix.services.chat.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.defix.services.chat.*;

@Configuration
public class ChatProviderConfig {
    private final ChatService chatService;
    private final MessageService messageService;
    private final EncryptionService encryptionService;

    @Autowired
    public ChatProviderConfig(ChatService chatService,
                              MessageService messageService,
                              EncryptionService encryptionService) {
        this.chatService = chatService;
        this.messageService = messageService;
        this.encryptionService = encryptionService;
    }

    @Bean(name = "chatProvider")
    public DecryptedChatProvider chatProvider() {
        return new DecryptedChatProvider(chatService, messageService);
    }

    @Bean(name = "messageProvider")
    public DecryptedMessageProvider messageProvider() {
        return new DecryptedMessageProvider(chatService, messageService);
    }

    @Bean(name = "privateKeyProvider")
    public DecryptedPrivateKeyProvider privateKeyProvider() {
        return new DecryptedPrivateKeyProvider(encryptionService);
    }

    @Bean(name = "publicKeyProvider")
    public DecryptedPublicKeyProvider publicKeyProvider() {
        return new DecryptedPublicKeyProvider(encryptionService);
    }
}
