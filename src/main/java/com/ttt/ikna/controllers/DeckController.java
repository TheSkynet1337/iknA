package com.ttt.ikna.controllers;

import com.ttt.ikna.dtos.DeckDTO;
import com.ttt.ikna.dtos.FlashCardDTO;
import com.ttt.ikna.services.DeckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/decks")
public class DeckController {
    private final DeckService deckService;
    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<DeckDTO> find(@PathVariable long id) {
        return deckService.find(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/due")
    public List<FlashCardDTO> findDue(@PathVariable long id) {
        return deckService.findDue(id);
    }
    @PostMapping
    public ResponseEntity<Long> createDeck(@RequestBody DeckDTO deckDTO) {
        DeckDTO createdDeckDTO = deckService.save(deckDTO);
        if (createdDeckDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdDeckDTO.getId());
    }
}
