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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeckService {
    private final DeckRepository deckRepository;
    private final DeckMapper deckMapper;
    private final FlashCardMapper flashCardMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<DeckDTO> find(Long id) {
        return deckRepository.findById(id).map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public List<DeckDTO> findAll() {
        return deckRepository.findAll().stream().map(this::convertToDto).toList();
    }

    @Transactional
    public DeckDTO save(DeckDTO deckDTO) {
        Deck deck = convertToEntity(deckDTO);
        User user = userRepository.findById(deckDTO.getUserId()).orElse(null);
        deck.setUser(user);
        return convertToDto(deckRepository.save(deck));
    }

    @Transactional
    public void delete(Long id) {
        deckRepository.deleteById(id);
    }

    public Deck convertToEntity(DeckDTO deckDTO) {
        Deck deck = deckMapper.deckDTOToDeck(deckDTO);
        deck.setUser(userRepository.findById(deckDTO.getUserId()).orElse(null));
        List<FlashCard> flashCards = deckDTO.getFlashCards().stream().map(flashCardMapper::flashCardDTOToFlashCard).toList();
        flashCards.forEach(flashCard -> flashCard.setDeck(deck));
        deck.setFlashCards(flashCards);
        return deck;
    }

    public DeckDTO convertToDto(Deck deck) {
        DeckDTO deckDTO = deckMapper.deckToDeckDTO(deck);
        deckDTO.setFlashCards(deck.getFlashCards().stream().map(flashCardMapper::flashCardToFlashCardDTO).toList());
        return deckDTO;
    }

    @Transactional
    public List<FlashCardDTO> findDue(long id) {
        Deck deck = deckRepository.findById(id).orElse(null);
        if (deck == null) {
            return new ArrayList<>();
        }
        List<FlashCard> flashCards = deck.getFlashCards();
        LocalDate now = LocalDate.now();
        List<FlashCard> dueCards = flashCards.stream().filter((card) -> card.getDueDate().isBefore(now)).toList();
        return dueCards.stream().map(flashCardMapper::flashCardToFlashCardDTO).toList();
    }
}
