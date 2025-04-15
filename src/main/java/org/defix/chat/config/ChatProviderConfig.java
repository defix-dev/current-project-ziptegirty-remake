package org.defix.chat.config;

import org.defix.chat.service.ChatService;
import org.defix.chat.service.EncryptionService;
import org.defix.chat.service.MessageService;
import org.defix.chat.service.provider.DecryptedChatProvider;
import org.defix.chat.service.provider.DecryptedMessageProvider;
import org.defix.chat.service.provider.DecryptedPrivateKeyProvider;
import org.defix.chat.service.provider.DecryptedPublicKeyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
