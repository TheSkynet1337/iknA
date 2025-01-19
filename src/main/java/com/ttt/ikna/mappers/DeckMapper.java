package com.ttt.ikna.mappers;

import com.ttt.ikna.dtos.DeckDTO;
import com.ttt.ikna.entities.Deck;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",uses= FlashCardMapper.class)
public interface DeckMapper {
    @Mapping(source = "user.id",target="userId")
    DeckDTO deckToDeckDTO(Deck deck);
    Deck deckDTOToDeck(DeckDTO deckDTO);
}
