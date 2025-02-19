package org.ziptegrity.services.chat.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ziptegrity.services.chat.ChatApiFacade;
import org.ziptegrity.services.chat.objects.ChatApiDTO;
import org.ziptegrity.services.chat.objects.ChatDetailsApiDTO;
import org.ziptegrity.services.chat.objects.MessageApiDTO;
import org.ziptegrity.services.user.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatApiController {
    private static final Logger logger = LoggerFactory.getLogger(ChatApiController.class);
    private final UserService userService;
    private final ChatApiFacade apiFacade;

    @Autowired
    public ChatApiController(UserService userService,
                             ChatApiFacade apiFacade) {
        this.userService = userService;
        this.apiFacade = apiFacade;
    }

    @GetMapping("/get_chats")
    public ResponseEntity<List<ChatApiDTO>> getChats(Principal principal) {
        logger.debug("Getting chats to "+principal.getName()+"...");
        return ResponseEntity.ok(apiFacade.prepareChatsByUserId(
                userService.findByUsername(principal.getName()).getId()
        ));
    }

    @GetMapping("/get_messages")
    public ResponseEntity<List<MessageApiDTO>> getMessages(@RequestParam("targetUserId") int targetUserId, Principal principal) {
        logger.debug("Getting messages to "+principal.getName()+"="+targetUserId);
        return ResponseEntity.ok(
                apiFacade.prepareMessagesBetweenUsers(
                        userService.findByUsername(principal.getName()).getId(),
                        targetUserId
                )
        );
    }

    @GetMapping("/get_chat_info")
    public ResponseEntity<ChatDetailsApiDTO> getChatInfo(@RequestParam("targetUserId") int targetUserId) {
        logger.debug("Getting chat info to: "+targetUserId);
        return ResponseEntity.ok(
          new ChatDetailsApiDTO(
                  userService.findById(targetUserId).getUsername(),
                  targetUserId
          )
        );
    }
}
