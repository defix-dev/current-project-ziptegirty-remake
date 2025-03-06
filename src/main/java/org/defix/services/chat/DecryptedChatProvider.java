package org.defix.services.chat;

import org.defix.database.postgresql.entities.Chat;
import org.defix.database.postgresql.entities.Message;
import org.defix.database.postgresql.entities.User;
import org.defix.services.chat.abstractions.Provider;
import org.defix.services.chat.exceptions.SingleMemberChatException;
import org.defix.services.chat.objects.ChatApiDTO;

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
