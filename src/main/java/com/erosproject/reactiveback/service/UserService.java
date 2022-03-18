package com.erosproject.reactiveback.service;

import com.erosproject.reactiveback.model.User;
import com.erosproject.reactiveback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    public Flux<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Mono<ResponseEntity<User>> findById(String userId) {
        return userRepository.findById(userId)
                .map(savedUser -> ResponseEntity.ok(savedUser))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    public Mono<ResponseEntity<User>> updateUser(String userId, User user) {
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

    public Mono<ResponseEntity<Void>> deleteUser(String userId) {
        return userRepository.findById(userId)
                .flatMap(existingUser -> {
                    existingUser.setDeleted(Boolean.TRUE);
                    userRepository.save(existingUser);
                    return Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
                })
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    public Flux<User> streamUsers() {
        return userRepository.findAll();
    }

}
