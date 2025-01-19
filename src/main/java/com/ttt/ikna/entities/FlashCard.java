package com.ttt.ikna.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Entity
public class FlashCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "deck_id", nullable = false)
    @Setter
    private Deck deck;
    @OneToOne(cascade = CascadeType.ALL)
    @Setter
    private FlashCardSide front;
    @OneToOne(cascade = CascadeType.ALL)
    @Setter
    private FlashCardSide back;
    @Setter
    private Date dueDate;
    @Setter
    private Date previousDueDate;

}

