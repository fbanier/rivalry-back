package org.example.rivalry.repository;

import org.example.rivalry.entity.Bracket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BracketRepository extends JpaRepository<Bracket, Long> {
}

