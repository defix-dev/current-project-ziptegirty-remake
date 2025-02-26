package org.ziptegrity.services.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.ziptegrity.database.postgresql.entities.Message;
import org.ziptegrity.services.chat.abstractions.BiProvider;
import org.ziptegrity.services.chat.objects.MessageApiDTO;

import java.util.Base64;
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
