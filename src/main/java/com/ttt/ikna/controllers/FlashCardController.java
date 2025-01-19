package com.ttt.ikna.controllers;

import com.ttt.ikna.dtos.FlashCardDTO;
import com.ttt.ikna.entities.FlashCard;
import com.ttt.ikna.repositories.FlashCardRepository;
import com.ttt.ikna.services.FlashCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/flashcards")
public class FlashCardController {
    private final FlashCardService flashCardService;
    public FlashCardController(FlashCardService flashCardService) {
        this.flashCardService = flashCardService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<FlashCardDTO> find(@PathVariable long id) {
        return flashCardService.find(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PatchMapping("/{id}")
    public ResponseEntity<FlashCardDTO> passFail(@PathVariable long id ,@RequestParam boolean passed) {
        Optional<FlashCardDTO> updatedFlashCard = flashCardService.passFail(id,passed);
        return updatedFlashCard.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<FlashCardDTO> create(@RequestBody FlashCardDTO flashCardDTO) {
        FlashCardDTO newFlashCard = flashCardService.save(flashCardDTO);
        return ResponseEntity.ok(newFlashCard);
    }
}
