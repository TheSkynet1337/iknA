package com.ttt.ikna.repositories;

import com.ttt.ikna.entities.FlashCard;
import com.ttt.ikna.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
}
