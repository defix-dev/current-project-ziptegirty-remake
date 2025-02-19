package org.ziptegrity.services.chat.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.ziptegrity.services.chat.exceptions.ChatNotFoundException;
import org.ziptegrity.services.chat.exceptions.MessageAccessDeniedException;
import org.ziptegrity.services.user.UserService;

import java.security.Principal;
import java.util.Map;

@Service
public class AuthSubscribeInterceptor implements ChannelInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthSubscribeInterceptor.class);
    private final UserService userService;

    @Autowired
    public AuthSubscribeInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String destination = accessor.getDestination();

        logger.debug("Start checking subscribing");

        if (!accessor.getCommand().name().equals("SUBSCRIBE") || !destination.contains("/chat/listen")) return message;
        int expectedId = Integer.parseInt(destination.substring(destination.lastIndexOf('/') + 1));

        logger.debug("Checking authentication");

        String username = (String) accessor.getSessionAttributes().get("username");

        logger.debug("Provided username: "+username);
        logger.debug("Providing userid");

        int userId = userService.findByUsername(username).getId();

        logger.debug("userid:"+userId);

        if(expectedId != userId) throw new MessageAccessDeniedException();
        return message;
    }
}
