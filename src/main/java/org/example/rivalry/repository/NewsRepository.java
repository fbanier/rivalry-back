package org.example.rivalry.repository;

import org.example.rivalry.entity.Bracket;
import org.example.rivalry.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}

