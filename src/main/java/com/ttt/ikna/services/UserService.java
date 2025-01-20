package com.ttt.ikna.services;

import com.ttt.ikna.dtos.DeckDTO;
import com.ttt.ikna.dtos.UserDTO;
import com.ttt.ikna.entities.User;
import com.ttt.ikna.mappers.DeckMapper;
import com.ttt.ikna.mappers.UserMapper;
import com.ttt.ikna.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final DeckMapper deckMapper;

    @Transactional(readOnly = true)
    public Optional<UserDTO> find(Long id) {
        return userRepository.findById(id).map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(this::convertToDto).toList();
    }

    @Transactional
    public UserDTO save(UserDTO userDTO) {
        return convertToDto(userRepository.save(convertToEntity(userDTO)));
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User convertToEntity(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        user.setDecks(userDTO.getDecks().stream().map(deckMapper::deckDTOToDeck).toList());
        return user;
    }

    public UserDTO convertToDto(User user) {
        UserDTO userDTO = userMapper.userToUserDTO(user);
        List<DeckDTO> deckDTOs = user.getDecks().stream().map(deckMapper::deckToDeckDTO).toList();
        userDTO.setDecks(deckDTOs);
        return userDTO;
    }
}
