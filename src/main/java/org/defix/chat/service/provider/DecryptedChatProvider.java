package org.defix.chat.service.provider;

import org.defix.chat.exception.SingleMemberChatException;
import org.defix.chat.api.dto.response.ChatApiDTO;
import org.defix.chat.service.ChatService;
import org.defix.chat.service.MessageService;
import org.defix.chat.util.ChatCryptUtils;
import org.defix.database.postgresql.entity.Chat;
import org.defix.database.postgresql.entity.Message;
import org.defix.database.postgresql.entity.User;

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
