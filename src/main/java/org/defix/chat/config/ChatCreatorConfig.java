package org.defix.chat.config;

import org.defix.chat.service.creator.EncryptedChatKeysCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.defix.chat.service.creator.EncryptedMessageCreator;
import org.defix.chat.service.EncryptionService;
import org.defix.chat.service.MessageService;

@Configuration
public class ChatCreatorConfig {
    private final EncryptionService encryptionService;
    private final MessageService messageService;

    @Autowired
    public ChatCreatorConfig(EncryptionService encryptionService, MessageService messageService) {
        this.encryptionService = encryptionService;
        this.messageService = messageService;
    }

    @Bean(name = "keysCreator")
    public EncryptedChatKeysCreator keysCreator() {
        return new EncryptedChatKeysCreator(encryptionService);
    }

    @Bean(name = "messageCreator")
    public EncryptedMessageCreator messageCreator() {
        return new EncryptedMessageCreator(messageService);
    }
}
