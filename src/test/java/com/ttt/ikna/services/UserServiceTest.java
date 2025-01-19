package com.ttt.ikna.services;

import com.ttt.ikna.entities.Deck;
import com.ttt.ikna.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

//    @Test
//    public void userTestDataExists(){
//        List<User> users = userService.findAll();
//        Assertions.assertEquals(3, users.size());
//    }
//    @Test
//    public void canCreateUser(){
//        User user = new User();
//        user.setUsername("test");
//        User newUser = userService.save(user);
//        Assertions.assertNotNull(newUser);
//        Assertions.assertNotNull(newUser.getId());
//        Assertions.assertEquals(user.getUsername(), newUser.getUsername());
//    }
//    @Test
//    public void canGetUserById(){
//        Optional<User> user = userService.find(1L);
//        Assertions.assertTrue(user.isPresent());
//        Assertions.assertEquals("TTT", user.get().getUsername());
//        Assertions.assertEquals(1, user.get().getDecks().size());
//    }
}
