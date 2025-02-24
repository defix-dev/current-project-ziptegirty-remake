package org.ziptegrity.services.chat.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ziptegrity.services.chat.abstractions.Provider;
import org.ziptegrity.services.chat.objects.ChatApiDTO;
import org.ziptegrity.services.chat.objects.ChatDetailsApiDTO;
import org.ziptegrity.services.user.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatControllerV1 {
    private final static Logger logger = LoggerFactory.getLogger(ChatControllerV1.class);
    private final Provider<List<ChatApiDTO>, Integer> chatProvider;
    private final UserService userService;

    @Autowired
    public ChatControllerV1(@Qualifier("chatProvider") Provider<List<ChatApiDTO>, Integer> chatProvider,
                            UserService userService) {
        this.chatProvider = chatProvider;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<ChatApiDTO>> getChats(Principal principal) {
        logger.debug(STR."Getting chats to \{principal.getName()}...");
        return ResponseEntity.ok(chatProvider.provide(userService.findByUsername(principal.getName()).getId()));
    }

    @GetMapping("/metadata")
    public ResponseEntity<ChatDetailsApiDTO> getChatInfo(@RequestParam("targetUserId") int targetUserId) {
        logger.debug(STR."Getting chat info to: \{targetUserId}");
        return ResponseEntity.ok(new ChatDetailsApiDTO(userService.findById(targetUserId).getUsername(), targetUserId));
    }
}
