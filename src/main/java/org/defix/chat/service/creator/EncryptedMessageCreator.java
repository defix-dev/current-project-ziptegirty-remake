package org.defix.chat.service.creator;

import org.defix.chat.api.dto.request.MessageCreationDetails;
import org.defix.chat.service.MessageService;
import org.defix.chat.util.ChatCryptUtils;
import org.defix.database.postgresql.entity.Message;

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
