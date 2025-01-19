package com.ttt.ikna.mappers;

import com.ttt.ikna.dtos.DeckDTO;
import com.ttt.ikna.dtos.FlashCardDTO;
import com.ttt.ikna.entities.Deck;
import com.ttt.ikna.entities.FlashCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses=FlashCardSideMapper.class)
public interface FlashCardMapper {
    @Mapping(source = "deck.id",target="deckId")
    FlashCardDTO flashCardToFlashCardDTO(FlashCard flashCard);
    FlashCard flashCardDTOToFlashCard(FlashCardDTO flashCardDTO);
}
