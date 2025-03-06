package org.defix.services.chat.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.defix.services.chat.abstractions.BiProvider;
import org.defix.services.chat.objects.MessageApiDTO;
import org.defix.services.user.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageControllerV1 {
    private final static Logger logger = LoggerFactory.getLogger(MessageControllerV1.class);
    private final UserService userService;
    private final BiProvider<List<MessageApiDTO>, Integer> messageProvider;

    @Autowired
    public MessageControllerV1(UserService userService,
                               @Qualifier("messageProvider") BiProvider<List<MessageApiDTO>, Integer> messageProvider) {
        this.userService = userService;
        this.messageProvider = messageProvider;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MessageApiDTO>> getMessages(@RequestParam("targetUserId") int targetUserId, Principal principal) {
        logger.debug(STR."Getting messages to \{principal.getName()}=\{targetUserId}");
        return ResponseEntity.ok(
                messageProvider.provide(
                        userService.findByUsername(principal.getName()).getId(),
                        targetUserId
                )
        );
    }
}
