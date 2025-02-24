package org.ziptegrity.database.postgresql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ziptegrity.database.postgresql.entities.ChatKeys;

@Repository
public interface ChatKeysRepository extends JpaRepository<ChatKeys, Integer> {
}
