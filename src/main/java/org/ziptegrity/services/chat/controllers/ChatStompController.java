package org.ziptegrity.services.chat.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.ziptegrity.database.postgresql.entities.Message;
import org.ziptegrity.services.chat.ChatService;
import org.ziptegrity.services.chat.ChatStompFacade;
import org.ziptegrity.services.chat.MessageService;
import org.ziptegrity.services.chat.exceptions.MessageAccessDeniedException;
import org.ziptegrity.services.chat.objects.MessageDTO;
import org.ziptegrity.services.chat.objects.MessageDetails;
import org.ziptegrity.services.user.UserService;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class ChatStompController {
    private static final Logger logger = LoggerFactory.getLogger(ChatStompController.class);
    private final ChatStompFacade chatFacade;
    private final UserService userService;

    @Autowired
    public ChatStompController(ChatStompFacade chatFacade,
                               UserService userService) {
        this.chatFacade = chatFacade;
        this.userService = userService;
    }

    @MessageMapping("/chat/{id}")
    @PreAuthorize("isAuthenticated()")
    public void sendMessage(@DestinationVariable("id") int id, String message, Principal principal) {
        logger.debug("Message to send: "+message);
        chatFacade.sendMessageToChat(userService.findByUsername(principal.getName()).getId(),
                id, message);
    }
}
