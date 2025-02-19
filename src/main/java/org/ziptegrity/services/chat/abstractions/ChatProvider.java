package org.ziptegrity.services.chat.abstractions;

import org.ziptegrity.services.chat.objects.ChatApiDTO;

import java.util.List;

public interface ChatProvider<DTO> {
    List<DTO> provideChatsByUserId(int userId);
}
