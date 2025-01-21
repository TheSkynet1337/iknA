package com.ttt.ikna.services;

import com.ttt.ikna.dtos.FlashCardDTO;
import com.ttt.ikna.dtos.FlashCardSideDTO;
import com.ttt.ikna.entities.Deck;
import com.ttt.ikna.entities.FlashCard;
import com.ttt.ikna.entities.FlashCardSide;
import com.ttt.ikna.entities.User;
import com.ttt.ikna.mappers.FlashCardMapper;
import com.ttt.ikna.mappers.FlashCardSideMapper;
import com.ttt.ikna.repositories.DeckRepository;
import com.ttt.ikna.repositories.FlashCardRepository;
import com.ttt.ikna.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FlashCardService {
    private final FlashCardRepository flashCardRepository;
    private final FlashCardMapper flashCardMapper;
    private final FlashCardSideMapper flashCardSideMapper;
    private final DeckRepository deckRepository;

    @Transactional(readOnly = true)
    public Optional<FlashCardDTO> find(Long id) {
        return flashCardRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public List<FlashCardDTO> findAll() {
        return flashCardRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public FlashCardDTO save(FlashCardDTO flashCardDTO) {
        FlashCard flashCard = convertToEntity(flashCardDTO);
        Deck deck = deckRepository.findById(flashCardDTO.getDeckId()).orElse(null);
        flashCard.setDeck(deck);
        return convertToDTO(flashCardRepository.save(flashCard));
    }

    @Transactional
    public void delete(Long id) {
        flashCardRepository.deleteById(id);
    }

    public FlashCardDTO convertToDTO(FlashCard flashCard) {
        FlashCardDTO flashCardDTO = flashCardMapper.flashCardToFlashCardDTO(flashCard);
        flashCardDTO.setFront(flashCardSideMapper.flashCardSideToFlashCardSideDTO(flashCard.getFront()));
        flashCardDTO.setBack(flashCardSideMapper.flashCardSideToFlashCardSideDTO(flashCard.getBack()));
        return flashCardDTO;
    }

    public FlashCard convertToEntity(FlashCardDTO flashCardDTO) {
        FlashCard flashCard = flashCardMapper.flashCardDTOToFlashCard(flashCardDTO);
        flashCard.setDeck(deckRepository.findById(flashCardDTO.getDeckId()).orElse(null));
        flashCard.setFront(flashCardSideMapper.flashCardDTOToFlashCardSide(flashCardDTO.getFront()));
        flashCard.setBack(flashCardSideMapper.flashCardDTOToFlashCardSide(flashCardDTO.getBack()));
        return flashCard;
    }

    @Transactional
    public Optional<FlashCardDTO> passFail(long id, boolean pass) {
        FlashCard flashCard = flashCardRepository.findById(id).orElse(null);
        if (flashCard == null) {
            return Optional.empty();
        }
        LocalDate due = flashCard.getDueDate();
        LocalDate previousDue = flashCard.getPreviousDueDate();
        Period delta = Period.between(previousDue, due);
        LocalDate newDue;
        if (pass) {
            //new due is old due date + 2*delta + 1 day
            newDue = due.plus(Period.ofDays(delta.getDays() * 2 + 1));
        } else {
            //new due is old due date + delta/2
            newDue = due.plus(Period.ofDays(Math.round(delta.getDays() / 2.0f)));
        }
        flashCard.setPreviousDueDate(due);
        flashCard.setDueDate(newDue);
        return Optional.of(convertToDTO(flashCardRepository.save(flashCard)));
    }
}
