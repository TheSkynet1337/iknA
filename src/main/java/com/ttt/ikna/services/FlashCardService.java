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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlashCardService {
    private final FlashCardRepository flashCardRepository;
    private final FlashCardMapper flashCardMapper;
    private final FlashCardSideMapper flashCardSideMapper;
    private final DeckRepository deckRepository;

    public FlashCardService(FlashCardRepository flashCardRepository, FlashCardMapper flashCardMapper, FlashCardSideMapper flashCardSideMapper, DeckRepository deckRepository) {
        this.flashCardRepository = flashCardRepository;
        this.flashCardMapper = flashCardMapper;
        this.flashCardSideMapper = flashCardSideMapper;
        this.deckRepository = deckRepository;
    }
    public Optional<FlashCardDTO> find(Long id){
        return flashCardRepository.findById(id).map(this::convertToDTO);
    }
    public List<FlashCardDTO> findAll(){
        return flashCardRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public FlashCardDTO save(FlashCardDTO flashCardDTO){
        FlashCard flashCard = convertToEntity(flashCardDTO);
        Deck deck = deckRepository.findById(flashCardDTO.getDeckId()).orElse(null);
        flashCard.setDeck(deck);
        return convertToDTO(flashCardRepository.save(flashCard));
    }
    public void delete(Long id){
        flashCardRepository.deleteById(id);
    }
    public FlashCardDTO convertToDTO(FlashCard flashCard){
        FlashCardDTO flashCardDTO = flashCardMapper.flashCardToFlashCardDTO(flashCard);
        flashCardDTO.setFront(flashCardSideMapper.flashCardSideToFlashCardSideDTO(flashCard.getFront()));
        flashCardDTO.setBack(flashCardSideMapper.flashCardSideToFlashCardSideDTO(flashCard.getBack()));
        return flashCardDTO;
    }
    public FlashCard convertToEntity(FlashCardDTO flashCardDTO){
        FlashCard flashCard = flashCardMapper.flashCardDTOToFlashCard(flashCardDTO);
        flashCard.setDeck(deckRepository.findById(flashCardDTO.getDeckId()).orElse(null));
        flashCard.setFront(flashCardSideMapper.flashCardDTOToFlashCardSide(flashCardDTO.getFront()));
        flashCard.setBack(flashCardSideMapper.flashCardDTOToFlashCardSide(flashCardDTO.getBack()));
        return flashCard;
    }

    public Optional<FlashCardDTO> passFail(long id,boolean pass) {
        FlashCard flashCard = flashCardRepository.findById(id).orElse(null);
        if (flashCard == null) {
            return Optional.empty();
        }
        Date due = flashCard.getDueDate();
        Date previousDue = flashCard.getPreviousDueDate();
        long delta = Math.abs(due.getTime() - previousDue.getTime());//in milliseconds
        //a day is 86 400 000 milliseconds long
        Date newDue = new Date(due.getTime() + Math.round(delta * 1.5f) + 86400000);
        flashCard.setPreviousDueDate(due);
        flashCard.setDueDate(newDue);
        return Optional.of(convertToDTO(flashCardRepository.save(flashCard)));
    }
}
