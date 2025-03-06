package org.defix.services.chat;

import org.defix.database.postgresql.entities.Message;
import org.defix.services.chat.abstractions.ReturnedCreator;
import org.defix.services.chat.objects.MessageCreationDetails;

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
