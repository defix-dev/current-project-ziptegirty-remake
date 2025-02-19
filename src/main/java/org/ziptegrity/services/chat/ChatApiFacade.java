package org.ziptegrity.services.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.ziptegrity.database.postgresql.entities.Chat;
import org.ziptegrity.database.postgresql.entities.Message;
import org.ziptegrity.database.postgresql.entities.User;
import org.ziptegrity.services.chat.abstractions.ChatProvider;
import org.ziptegrity.services.chat.abstractions.MessageProvider;
import org.ziptegrity.services.chat.exceptions.SingleMemberChatException;
import org.ziptegrity.services.chat.objects.ChatApiDTO;
import org.ziptegrity.services.chat.objects.ChatDetailsApiDTO;
import org.ziptegrity.services.chat.objects.MessageApiDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatApiFacade {
    private final ChatProvider<ChatApiDTO> chatProvider;
    private final MessageProvider<MessageApiDTO> messageProvider;

    public ChatApiFacade(ChatProvider<ChatApiDTO> chatProvider,
                         MessageProvider<MessageApiDTO> messageProvider) {
        this.chatProvider = chatProvider;
        this.messageProvider = messageProvider;
    }

    public List<ChatApiDTO> prepareChatsByUserId(int userId) {
        return chatProvider.provideChatsByUserId(userId);
    }

    public List<MessageApiDTO> prepareMessagesBetweenUsers(int userId, int targetUserId) {
        return messageProvider.provideMessagesBetweenUsers(userId, targetUserId);
    }
}
