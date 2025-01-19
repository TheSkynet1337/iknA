package com.ttt.ikna.services;

import com.ttt.ikna.dtos.DeckDTO;
import com.ttt.ikna.dtos.FlashCardDTO;
import com.ttt.ikna.entities.Deck;
import com.ttt.ikna.entities.FlashCard;
import com.ttt.ikna.entities.User;
import com.ttt.ikna.mappers.DeckMapper;
import com.ttt.ikna.mappers.FlashCardMapper;
import com.ttt.ikna.repositories.DeckRepository;
import com.ttt.ikna.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeckService {
    private final DeckRepository deckRepository;

    private final DeckMapper deckMapper;
    private final FlashCardMapper flashCardMapper;
    private final UserRepository userRepository;

    public DeckService(DeckRepository deckRepository, DeckMapper deckMapper, FlashCardMapper flashCardMapper, UserRepository userRepository) {
        this.deckRepository = deckRepository;
        this.deckMapper = deckMapper;
        this.flashCardMapper = flashCardMapper;
        this.userRepository = userRepository;
    }
    public Optional<DeckDTO> find(Long id){
        return deckRepository.findById(id).map(this::convertToDto);
    }
    public List<DeckDTO> findAll(){
        return deckRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }
    public DeckDTO save(DeckDTO deckDTO){
        Deck deck = convertToEntity(deckDTO);
        User user = userRepository.findById(deckDTO.getUserId()).orElse(null);
        deck.setUser(user);
        return convertToDto(deckRepository.save(deck));
    }
    public void delete(Long id){
        deckRepository.deleteById(id);
    }
    public Deck convertToEntity(DeckDTO deckDTO){
        Deck deck = deckMapper.deckDTOToDeck(deckDTO);
        deck.setUser(userRepository.getById(deckDTO.getUserId()));
        List<FlashCard> flashCards = deckDTO.getFlashCards().stream().map(flashCardMapper::flashCardDTOToFlashCard).toList();
        flashCards.forEach(flashCard -> flashCard.setDeck(deck));
        deck.setFlashCards(flashCards);
        return deck;
    }
    public DeckDTO convertToDto(Deck deck){
        DeckDTO deckDTO = deckMapper.deckToDeckDTO(deck);
        deckDTO.setFlashCards(deck.getFlashCards().stream().map(flashCardMapper::flashCardToFlashCardDTO).toList());
        return deckDTO;
    }

    public List<FlashCardDTO> findDue(long id) {
        Deck deck = deckRepository.findById(id).orElse(null);
        if(deck == null) {
            return new ArrayList<>();
        }
        List<FlashCard> flashCards = deck.getFlashCards();
        Date now = new Date();
        List<FlashCard> dueCards = flashCards.stream().filter((card) -> card.getDueDate().before(now)).toList();
        return dueCards.stream().map(flashCardMapper::flashCardToFlashCardDTO).toList();
    }
}
