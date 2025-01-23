package com.ttt.ikna.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Entity
public class FlashCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false)
    @Setter
    private Deck deck;
    @Setter
    private String front;
    @Setter
    private String back;
    @Setter
    private LocalDate dueDate;
    @Setter
    private LocalDate previousDueDate;

}

