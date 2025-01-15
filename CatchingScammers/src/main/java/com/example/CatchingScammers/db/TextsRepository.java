package com.example.CatchingScammers.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TextsRepository extends JpaRepository<TextsEntity, Long> {}
