package org.defix.chat.service;

import org.defix.chat.exception.ChatAlreadyExistException;
import org.defix.chat.exception.ChatNotFoundException;
import org.defix.chat.exception.EmptyChatsException;
import org.defix.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.defix.database.postgresql.entity.Chat;
import org.defix.database.postgresql.entity.User;
import org.defix.database.postgresql.repository.ChatRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private final ChatRepository chatRepo;
    private final UserService userService;

    @Autowired
    public ChatService(ChatRepository chatRepo,
                       UserService userService) {
        this.chatRepo = chatRepo;
        this.userService = userService;
    }

    public Chat createChat(int aId, int bId) {
        return createChat(
                userService.findById(aId),
                userService.findById(bId)
        );
    }

    private Chat createChat(User a, User b) {
        if(hasChat(a.getId(), b.getId())) throw new ChatAlreadyExistException();
        Chat chat = new Chat();
        chat.setMembers(Set.of(
                a, b
        ));
        Chat saved = chatRepo.save(chat);
        logger.debug(STR."Chat saved with id: \{chat.getId()}");
        return saved;
    }

    public boolean hasChat(int aId, int bId) {
        boolean has = chatRepo.hasChat(aId, bId);
        logger.debug(STR."Chat between \{aId} \{bId}: \{has}");
        return has;
    }

    public Chat getChatBetweenUsers(int aId, int bId) {
        Optional<Chat> found = Optional.ofNullable(chatRepo.getChat(aId, bId));
        if(found.isEmpty()) throw new ChatNotFoundException();
        logger.debug(STR."Chat loaded with id: \{found.get().getId()}");
        return found.get();
    }

    public Chat getChatById(int chatId) {
        Optional<Chat> chat = chatRepo.findById(chatId);
        if(chat.isEmpty()) throw new ChatNotFoundException();
        return chat.get();
    }

    public List<Chat> getSortedChatsByUserId(int userId) {
        List<Chat> chats = chatRepo.getSortedChatsByUserId(userId);
        if(chats.isEmpty()) throw new EmptyChatsException();
        logger.debug(STR."Received chats: \{chats}");
        return chats;
    }
}
