package org.ziptegrity.services.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.ziptegrity.services.chat.abstractions.MessageCreator;
import org.ziptegrity.services.chat.exceptions.MessageAccessDeniedException;
import org.ziptegrity.services.chat.objects.MessageDTO;
import org.ziptegrity.services.chat.objects.MessageDetails;
import org.ziptegrity.services.user.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class ChatStompFacade {
    private static final Logger logger = LoggerFactory.getLogger(ChatStompFacade.class);
    private final SimpMessagingTemplate message;
    private final ChatService chatService;
    private final MessageCreator messageCreator;

    @Autowired
    public ChatStompFacade(SimpMessagingTemplate message,
                           ChatService chatService,
                           MessageCreator messageCreator) {
        this.message = message;
        this.chatService = chatService;
        this.messageCreator = messageCreator;
    }

    public void sendMessageToChat(int senderId, int destinationId, String message) {
        if(!chatService.hasChat(senderId, destinationId)) {
            chatService.createChat(senderId, destinationId);
            logger.info("Created chat between "+senderId+" "+destinationId);
        }

        LocalDateTime createdAt = messageCreator.createMessageBetweenUsers(senderId, destinationId, new MessageDetails(
                senderId, message
        )).getCreatedAt();

        MessageDTO dto = new MessageDTO(
                senderId, message, createdAt
        );

        this.message.convertAndSend("/chat/listen/"+destinationId, dto,
                Collections.singletonMap("content-type", "application/json"));
        this.message.convertAndSend("/chat/listen/"+senderId, dto,
                Collections.singletonMap("content-type", "application/json"));
    }
}
