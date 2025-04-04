package org.defix.database.postgresql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.defix.database.postgresql.entities.ChatKeys;

@Repository
public interface ChatKeysRepository extends JpaRepository<ChatKeys, Integer> {
}
