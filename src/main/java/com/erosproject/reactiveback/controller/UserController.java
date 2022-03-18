package com.erosproject.reactiveback.controller;

import com.erosproject.reactiveback.model.User;
import com.erosproject.reactiveback.repository.UserRepository;
import com.erosproject.reactiveback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public Flux<User> listUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<User>> getUserById(@PathVariable (value = "id") String userId) {
        return userService.findById(userId);
    }

    @PutMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<User>> updateUser(@PathVariable (value = "id") String userId,
                                                 @Valid @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable (value = "id") String userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping(value = "/stream/user", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<User> streamUsers() {
//        log.info("<<<<< Stream of Users >>>>>");
        return userService.streamUsers();
    }

}
