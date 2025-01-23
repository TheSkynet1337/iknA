package com.ttt.ikna.repositories;

import com.ttt.ikna.entities.Deck;
import com.ttt.ikna.entities.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
}
