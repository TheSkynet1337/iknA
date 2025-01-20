package com.ttt.ikna.services;

import com.ttt.ikna.dtos.FlashCardSideDTO;
import com.ttt.ikna.entities.FlashCardSide;
import com.ttt.ikna.entities.User;
import com.ttt.ikna.mappers.FlashCardSideMapper;
import com.ttt.ikna.repositories.FlashCardSideRepository;
import com.ttt.ikna.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FlashCardSideService {
    private final FlashCardSideRepository flashCardSideRepository;
    private final FlashCardSideMapper flashCardSideMapper;

    @Transactional(readOnly = true)
    public Optional<FlashCardSideDTO> find(Long id) {
        return flashCardSideRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public List<FlashCardSideDTO> findAll() {
        return flashCardSideRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public FlashCardSideDTO save(FlashCardSideDTO flashCardSideDTO) {
        return convertToDTO(flashCardSideRepository.save(convertToEntity(flashCardSideDTO)));
    }

    @Transactional
    public void delete(Long id) {
        flashCardSideRepository.deleteById(id);
    }

    public FlashCardSideDTO convertToDTO(FlashCardSide flashCardSide) {
        return flashCardSideMapper.flashCardSideToFlashCardSideDTO(flashCardSide);
    }

    public FlashCardSide convertToEntity(FlashCardSideDTO flashCardSideDTO) {
        return flashCardSideMapper.flashCardDTOToFlashCardSide(flashCardSideDTO);
    }
}
