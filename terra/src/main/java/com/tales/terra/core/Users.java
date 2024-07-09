package com.tales.terra.core;

public interface Users {
    public static User getUser(String id) {
        return new User();
    }

    public static User createUser() {
        return new User();
    }

    public static User updateUser(String id) {
        return new User();
    }

    public static User deleteUser(String id) {
        return new User();
    }
}
