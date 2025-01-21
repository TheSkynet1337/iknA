package com.ttt.ikna.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlashCardDTO {
    private Long id;
    private Long deckId;
    private FlashCardSideDTO front;
    private FlashCardSideDTO back;
    private LocalDate dueDate;
    private LocalDate previousDueDate;
}
