package org.ziptegrity.services.chat.abstractions;

import org.ziptegrity.database.postgresql.entities.Message;
import org.ziptegrity.services.chat.objects.MessageDetails;

import java.util.List;

public interface MessageCreator {
    Message createMessageBetweenUsers(int aId, int bId, MessageDetails details);
}
