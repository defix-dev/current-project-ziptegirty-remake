package org.defix.services.chat.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.defix.services.chat.ChatStompFacade;
import org.defix.services.user.UserService;

import java.security.Principal;

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
        logger.debug(STR."Message to send: \{message}");
        chatFacade.sendMessageToChat(userService.findByUsername(principal.getName()).getId(),
                id, message);
    }
}
