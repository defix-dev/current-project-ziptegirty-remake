package org.ziptegrity.services.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ziptegrity.database.postgresql.entities.Message;
import org.ziptegrity.services.chat.abstractions.MessageProvider;
import org.ziptegrity.services.chat.objects.MessageApiDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DecryptedMessageProvider implements MessageProvider<MessageApiDTO> {
    private final ChatService chatService;
    private final MessageService messageService;

    @Autowired
    public DecryptedMessageProvider(ChatService chatService,
                                    MessageService messageService) {
        this.messageService = messageService;
        this.chatService = chatService;
    }

    @Override
    public List<MessageApiDTO> provideMessagesBetweenUsers(int aId, int bId) {
        List<Message> messages = messageService.getSortedMessagesByChatId(chatService.getChatBetweenUsers(aId, bId).getId());
        return messages.stream().map(msg -> new MessageApiDTO(
                ChatCryptUtils.decryptMessage(msg.getMessage()),
                msg.getCreatedAt(),
                msg.getUser().getId())).collect(Collectors.toList());
    }
}
