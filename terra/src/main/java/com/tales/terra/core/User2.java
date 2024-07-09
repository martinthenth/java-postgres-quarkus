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
    public String deletedAt;

    public User2() {
        this.firstName = "Maritn";
        this.lastName = "Nijboer";
    }
}
