package org.ziptegrity.services.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.ziptegrity.database.postgresql.entities.Message;
import org.ziptegrity.services.chat.abstractions.ReturnedCreator;
import org.ziptegrity.services.chat.objects.MessageCreationDetails;
import org.ziptegrity.services.chat.objects.MessageDTO;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class ChatStompFacade {
    private static final Logger logger = LoggerFactory.getLogger(ChatStompFacade.class);
    private final SimpMessagingTemplate message;
    private final ChatService chatService;
    private final ReturnedCreator<Message, MessageCreationDetails> messageCreator;

    @Autowired
    public ChatStompFacade(SimpMessagingTemplate message,
                           ChatService chatService,
                           @Qualifier("messageCreator") ReturnedCreator<Message, MessageCreationDetails> messageCreator) {
        this.message = message;
        this.chatService = chatService;
        this.messageCreator = messageCreator;
    }

    public void sendMessageToChat(int senderId, int destinationId, String message) {
        if(!chatService.hasChat(senderId, destinationId)) {
            chatService.createChat(senderId, destinationId);
            logger.info(STR."Created chat between \{senderId} \{destinationId}");
        }

        LocalDateTime createdAt = messageCreator.create(
                new MessageCreationDetails(senderId, destinationId, message)
        ).getCreatedAt();

        MessageDTO dto = new MessageDTO(
                senderId, message, createdAt
        );

        this.message.convertAndSend(STR."/chat/listen/\{destinationId}", dto,
                Collections.singletonMap("content-type", "application/json"));
        this.message.convertAndSend(STR."/chat/listen/\{senderId}", dto,
                Collections.singletonMap("content-type", "application/json"));
    }
}
