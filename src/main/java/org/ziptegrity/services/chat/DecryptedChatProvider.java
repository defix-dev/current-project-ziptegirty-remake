package org.ziptegrity.services.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.ziptegrity.database.postgresql.entities.Chat;
import org.ziptegrity.database.postgresql.entities.Message;
import org.ziptegrity.database.postgresql.entities.User;
import org.ziptegrity.services.chat.abstractions.Provider;
import org.ziptegrity.services.chat.exceptions.SingleMemberChatException;
import org.ziptegrity.services.chat.objects.ChatApiDTO;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class DecryptedChatProvider implements Provider<List<ChatApiDTO>, Integer> {
    private final ChatService chatService;
    private final MessageService messageService;

    public DecryptedChatProvider(ChatService chatService,
                                 MessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @Override
    public List<ChatApiDTO> provide(Integer userId) {
        List<Chat> chats = chatService.getSortedChatsByUserId(userId);
        return chats.stream().map(chat -> {
            User target = chat.getMembers().stream().filter(u -> u.getId() != userId).findFirst()
                    .orElseThrow(SingleMemberChatException::new);
            Message lastMsg = messageService.getLastMessageByChatId(chat.getId());
            return new ChatApiDTO(
                    target.getUsername(), target.getId(),
                    ChatCryptUtils.decrypt(lastMsg.getMessage()),
                    lastMsg.getCreatedAt()
            );
        }).collect(Collectors.toList());
    }
}
