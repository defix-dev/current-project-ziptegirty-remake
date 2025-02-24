package org.ziptegrity.services.chat.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ziptegrity.services.chat.EncryptedChatKeysCreator;
import org.ziptegrity.services.chat.EncryptedMessageCreator;
import org.ziptegrity.services.chat.EncryptionService;
import org.ziptegrity.services.chat.MessageService;

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
