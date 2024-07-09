package com.tales.terra.core;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@ApplicationScoped
public class Users2 implements PanacheRepositoryBase<User2, UUID> {
    /** Attributes for creating a user. */
    public static class CreateAttrs {
        @NotBlank
        public String firstName;
        @NotBlank
        public String lastName;
    }

    // TODO: This is a mess
    /**
     * TODO
     * 
     * @param attrs
     * @return
     */
    @Transactional
    public User2 createUser(CreateAttrs attrs) {
        User2 user = new User2(attrs.firstName, attrs.lastName);
        persist(user);

        return user;
    }

    public User2 getUser(UUID id) {
        return new User2();
    }

    public User2 updateUser(UUID id) {
        return new User2();
    }

    public User2 deleteUser(UUID id) {
        return new User2();
    }
}
