package com.ttt.ikna.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeckDTO {
    private Long id;
    private Long userId;
    private String name;
    private List<FlashCardDTO> flashCards;
}
