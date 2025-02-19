package org.ziptegrity.services.chat.abstractions;

import java.util.List;

public interface MessageProvider<DTO> {
    List<DTO> provideMessagesBetweenUsers(int aId, int bId);
}