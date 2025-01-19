package com.ttt.ikna.mappers;

import com.ttt.ikna.dtos.DeckDTO;
import com.ttt.ikna.dtos.FlashCardSideDTO;
import com.ttt.ikna.entities.Deck;
import com.ttt.ikna.entities.FlashCard;
import com.ttt.ikna.entities.FlashCardSide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlashCardSideMapper {
    FlashCardSideDTO flashCardSideToFlashCardSideDTO(FlashCardSide flashCardSide);
    FlashCardSide flashCardDTOToFlashCardSide(FlashCardSideDTO flashCardSideDTO);
}
