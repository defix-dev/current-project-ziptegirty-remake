package org.ziptegrity.services.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ziptegrity.database.postgresql.entities.Message;
import org.ziptegrity.services.chat.abstractions.MessageCreator;
import org.ziptegrity.services.chat.objects.MessageDetails;

@Service
public class CryptedMessageCreator implements MessageCreator {
    private final MessageService messageService;

    @Autowired
    public CryptedMessageCreator(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public Message createMessageBetweenUsers(int aId, int bId, MessageDetails details) {
        return messageService.createMessageBetweenUsers(aId, bId, new MessageDetails(
                details.getSenderId(),
                ChatCryptUtils.encryptMessage(details.getMessage())
        ));
    }
}
