package org.ziptegrity.services.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.ziptegrity.database.postgresql.entities.Message;
import org.ziptegrity.services.chat.abstractions.ReturnedCreator;
import org.ziptegrity.services.chat.objects.MessageCreationDetails;

import java.util.Base64;

public class EncryptedMessageCreator implements ReturnedCreator<Message, MessageCreationDetails> {
    private final MessageService messageService;

    public EncryptedMessageCreator(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public Message create(MessageCreationDetails details) {
        return messageService.createMessageBetweenUsers(
                details.getSenderId(), details.getDestinationId(), ChatCryptUtils.encrypt(
                        details.getMessage())
        );
    }
}
