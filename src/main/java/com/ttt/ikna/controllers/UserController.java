package com.ttt.ikna.controllers;

import com.ttt.ikna.dtos.UserDTO;
import com.ttt.ikna.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> find(@PathVariable long id) {
        return userService.find(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }
}
