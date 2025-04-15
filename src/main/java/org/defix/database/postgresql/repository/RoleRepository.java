package org.defix.database.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.defix.database.postgresql.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
