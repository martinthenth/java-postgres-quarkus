package com.tales.terra.core;

public class User {
    // public final String firstName;
    public String firstName;
    public String lastName;

    public String fullName() {
        return firstName + " " + lastName;
    }
}
