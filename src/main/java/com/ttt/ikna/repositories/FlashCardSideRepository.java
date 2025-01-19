package com.ttt.ikna.repositories;

import com.ttt.ikna.entities.FlashCardSide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashCardSideRepository extends JpaRepository<FlashCardSide, Long> {
}
