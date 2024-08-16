package org.example.administrationservice.repository;

import java.util.Optional;
import org.example.administrationservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findFirstByUsername(String username);
}