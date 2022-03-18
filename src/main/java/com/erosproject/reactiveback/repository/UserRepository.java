package com.erosproject.reactiveback.repository;

import com.erosproject.reactiveback.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
