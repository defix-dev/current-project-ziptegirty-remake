package org.ziptegrity.services.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ziptegrity.database.postgresql.entities.Message;
import org.ziptegrity.database.postgresql.repositories.MessageRepository;
import org.ziptegrity.services.chat.exceptions.NoMessagesException;
import org.ziptegrity.services.chat.objects.MessageDetails;
import org.ziptegrity.services.user.UserService;

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

    public Message createMessageBetweenUsers(int aId, int bId, MessageDetails details) {
        return createMessageByChatId(
                chatService.getChatBetweenUsers(aId, bId).getId(),
                details
        );
    }

    public Message createMessageByChatId(int chatId, MessageDetails details) {
        Message msg = new Message();
        msg.setChat(chatService.findChatById(chatId));
        msg.setMessage(details.getMessage());
        msg.setUser(userService.findById(details.getSenderId()));

        Message saved = messageRepo.save(msg);
        logger.info("Message has been created with id: "+saved.getId());
        return saved;
    }

    public Message getLastMessageByChatId(int chatId) {
        Optional<Message> message = Optional.ofNullable(messageRepo.getLastMessageByChatId(chatId));
        if(message.isEmpty()) throw new NoMessagesException();
        logger.debug("Message received: "+message.get().getMessage());
        return message.get();
    }

    public List<Message> getSortedMessagesByChatId(int chatId) {
        List<Message> messages = messageRepo.getSortedMessagesByChatId(chatId);
        if(messages.isEmpty()) throw new NoMessagesException();
        logger.debug("Received messages count: "+messages.size());
        return messages;
    }
}
