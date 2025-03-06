package org.defix.services.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.defix.database.postgresql.entities.Message;
import org.defix.database.postgresql.repositories.MessageRepository;
import org.defix.services.chat.exceptions.NoMessagesException;
import org.defix.services.user.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private final MessageRepository messageRepo;
    private final ChatService chatService;
    private final UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepo, ChatService chatService,
                          UserService userService) {
        this.messageRepo = messageRepo;
        this.chatService = chatService;
        this.userService = userService;
    }

    public Message createMessageBetweenUsers(int senderId, int destinationId, String message) {
        return createMessageByChatId(
                chatService.getChatBetweenUsers(senderId, destinationId).getId(), senderId, message
        );
    }

    public Message createMessageByChatId(int chatId, int senderId, String message) {
        Message msg = new Message();
        msg.setChat(chatService.findChatById(chatId));
        msg.setMessage(message);
        msg.setUser(userService.findById(senderId));

        Message saved = messageRepo.save(msg);
        logger.info(STR."Message has been created with id: \{saved.getId()}");
        return saved;
    }

    public Message getLastMessageByChatId(int chatId) {
        Optional<Message> message = Optional.ofNullable(messageRepo.getLastMessageByChatId(chatId));
        if(message.isEmpty()) throw new NoMessagesException();
        logger.debug(STR."Message received: \{message.get().getMessage()}");
        return message.get();
    }

    public List<Message> getSortedMessagesByChatId(int chatId) {
        List<Message> messages = messageRepo.getSortedMessagesByChatId(chatId);
        if(messages.isEmpty()) throw new NoMessagesException();
        logger.debug(STR."Received messages count: \{messages.size()}");
        return messages;
    }
}
