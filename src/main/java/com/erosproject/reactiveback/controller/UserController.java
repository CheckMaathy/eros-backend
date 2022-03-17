package com.erosproject.reactiveback.controller;

import com.erosproject.reactiveback.model.User;
import com.erosproject.reactiveback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController("/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public Flux<User> listUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<User>> getUserById(@PathVariable (value = "id") String userId) {
        return userRepository.findById(userId)
                .map(savedUser -> ResponseEntity.ok(savedUser))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<User>> updateUser(@PathVariable (value = "id") String userId,
                                                 @Valid @RequestBody User user) {
        return userRepository.findById(userId)
                .flatMap(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setNickname(user.getNickname());
                    existingUser.setEmail(user.getEmail());

                    return userRepository.save(existingUser);
                })
                .map(updateUser -> new ResponseEntity<>(updateUser, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable (value = "id") String userId) {
        return userRepository.findById(userId)
                .flatMap(existingUser -> {
                    existingUser.setDeleted(Boolean.TRUE);
                    userRepository.save(existingUser);
//                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)));
                    return Mono.just(new ResponseEntity<Void>(HttpStatus.OK));
                })
                            .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/stream/user", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<User> streamUsers() {
//        log.info("<<<<< Stream of Users >>>>>");
        return userRepository.findAll();
    }

}
