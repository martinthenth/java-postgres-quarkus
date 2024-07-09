package com.tales.terra.core;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

// TODO: I'd prefer it if it wasn't a class but an interface
public class Users2 implements PanacheRepositoryBase<User2, UUID> {
    /** Attributes for creating a user. */
    public static class CreateAttrs {
        @NotBlank
        public String firstName;
        @NotBlank
        public String lastName;
    }

    // TODO: This is a mess
    public User2 createUser(CreateAttrs attrs) {
        User2 user = new User2();
        user.firstName = attrs.firstName;
        user.lastName = attrs.lastName;
        persist(user);

        return user;
    }
}
