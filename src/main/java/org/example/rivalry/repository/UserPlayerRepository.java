package org.example.rivalry.repository;

import org.example.rivalry.entity.UserPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPlayerRepository extends JpaRepository<UserPlayer, Long> {
    List<UserPlayer> findByUserName(String userName);

    List<UserPlayer> findByEmail(String email);
}
