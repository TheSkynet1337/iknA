package com.ttt.ikna.mappers;

import com.ttt.ikna.dtos.DeckDTO;
import com.ttt.ikna.dtos.UserDTO;
import com.ttt.ikna.entities.Deck;
import com.ttt.ikna.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses= DeckMapper.class)
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
}
