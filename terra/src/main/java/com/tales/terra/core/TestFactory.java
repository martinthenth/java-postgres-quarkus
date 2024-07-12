package com.tales.terra.core;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.UUID;

import com.github.f4b6a3.uuid.UuidCreator;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TestFactory {
    @Inject
    Users users;

    @Transactional
    public User insertUser() {
        HashMap<String, Object> hashMap = new HashMap<>();

        return insertUser(hashMap);
    }

    @Transactional
    public User insertUser(HashMap<String, Object> hashMap) {
        UUID id = UuidCreator.getTimeOrderedEpoch();
        LocalDateTime timestamp = LocalDateTime.now(ZoneOffset.UTC);
        int randomInteger = (int) (Math.random() * 10_000 + 1);
        User user = new User();

        user.id = (UUID) hashMap.getOrDefault("id", id);
        user.firstName = (String) hashMap.getOrDefault("firstName", "Jane");
        user.lastName = (String) hashMap.getOrDefault("lastName", "Doe");
        user.emailAddress = (String) hashMap.getOrDefault("emailAddress", "jane.doe" + randomInteger + "@example.com");
        user.createdAt = (LocalDateTime) hashMap.getOrDefault("createdAt", timestamp);
        user.updatedAt = (LocalDateTime) hashMap.getOrDefault("updatedAt", timestamp);
        user.deletedAt = (LocalDateTime) hashMap.getOrDefault("deletedAt", null);

        users.persist(user);

        return user;
    }
}
