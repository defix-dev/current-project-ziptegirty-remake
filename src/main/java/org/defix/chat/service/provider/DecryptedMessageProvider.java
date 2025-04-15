package org.defix.chat.service.provider;

import org.defix.chat.service.ChatService;
import org.defix.chat.service.MessageService;
import org.defix.chat.util.ChatCryptUtils;
import org.defix.database.postgresql.entity.Message;
import org.defix.chat.api.dto.response.MessageApiDTO;

import java.util.List;
import java.util.stream.Collectors;

public class DecryptedMessageProvider implements BiProvider<List<MessageApiDTO>, Integer> {
    private final ChatService chatService;
    private final MessageService messageService;

    public DecryptedMessageProvider(ChatService chatService,
                                    MessageService messageService) {
        this.messageService = messageService;
        this.chatService = chatService;
    }

    @Override
    public List<MessageApiDTO> provide(Integer aId, Integer bId) {
        List<Message> messages = messageService.getSortedMessagesByChatId(chatService.getChatBetweenUsers(aId, bId).getId());
        return messages.stream().map(msg -> new MessageApiDTO(
                ChatCryptUtils.decrypt(msg.getMessage()),
                msg.getCreatedAt(),
                msg.getUser().getId())).collect(Collectors.toList());
    }
}
