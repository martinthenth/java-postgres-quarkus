package com.tales.terra.core;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class User2 {
    // TODO: Generate uuid v7
    // TODO: Make fields final
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;
    public String firstName;
    public String lastName;
    public String emailAddress;
    public String createdAt;
    public String updatedAt;
    public String deletedAt;

    public User2() {
        this.id = UUID.randomUUID();
    }

    public User2(String firstName, String lastName) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
