package org.defix.database.postgresql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.defix.database.postgresql.entities.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m WHERE m.chat.id=:chatId ORDER BY m.createdAt DESC LIMIT 1")
    Message getLastMessageByChatId(@Param("chatId") int chatId);

    @Query("SELECT m FROM Message m WHERE m.chat.id=:chatId ORDER BY m.createdAt ASC")
    List<Message> getSortedMessagesByChatId(@Param("chatId") int chatId);
}
