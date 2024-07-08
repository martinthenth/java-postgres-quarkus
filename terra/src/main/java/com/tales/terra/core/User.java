package com.tales.terra.core;

public class User {
    public final String id;
    public final String firstName;
    public final String lastName;
    public final String emailAddress;
    public final String createdAt;
    public final String updatedAt;
    public final String deletedAt;

    /**
     * Instantiates a User object for example purposeses.
     * DON'T USE THIS IN PRODUCTION CODE
     */
    public User() {
        this.id = "123";
        this.firstName = "firstName";
        this.lastName = "lastName";
        this.emailAddress = "emailAddress.com";
        this.createdAt = "now";
        this.updatedAt = "now";
        this.deletedAt = null;
    }

    public User(
            String id,
            String firstName,
            String lastName,
            String emailAddress,
            String createdAt,
            String updatedAt,
            String deletedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
