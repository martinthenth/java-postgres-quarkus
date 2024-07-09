package com.tales.terra.core;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@ApplicationScoped
public class Users implements PanacheRepositoryBase<User, UUID> {
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
    public User createUser(CreateAttrs attrs) {
        User user = new User(attrs.firstName, attrs.lastName);
        persist(user);

        return user;
    }

    public User getUser(UUID id) {
        return new User();
    }

    public User updateUser(UUID id) {
        return new User();
    }

    public User deleteUser(UUID id) {
        return new User();
    }
}
