package com.tales.terra.core;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;

@ApplicationScoped
public class Users implements PanacheRepositoryBase<User, UUID> {
    /** Attributes for creating a user. */
    public static class CreateAttrs {
        @NotBlank
        public String firstName;
        @NotBlank
        public String lastName;
        @NotBlank
        public String emailAddress;
    }

    /** Attributes for updating a user. */
    public static class UpdateAttrs {
        @NotBlank
        public String firstName;
        @NotBlank
        public String lastName;
    }

    /**
     * Gets the user by id.
     * 
     * @param id
     * @return
     */
    public User getUser(UUID id) {
        return findById(id);
    }

    /**
     * Creates a user with the attributes.
     * 
     * @param attrs
     * @return
     */
    @Transactional
    public User createUser(CreateAttrs attrs) {
        // TODO: This is a mess
        User user = new User();
        user.firstName = attrs.firstName;
        user.lastName = attrs.lastName;
        user.emailAddress = attrs.emailAddress;

        persist(user);
        flush();

        return user;
    }

    /**
     * Updates the user by id and with the attributes.
     * 
     * @param id
     * @param attrs
     * @return
     */
    @Transactional
    public User updateUser(UUID id, UpdateAttrs attrs) {
        User user = getUser(id);
        user.firstName = attrs.firstName;
        user.lastName = attrs.lastName;

        persist(user);
        flush();

        return user;
    }

    /**
     * Deletes the user by id.
     * 
     * @param id
     * @return
     */
    @Transactional
    public User deleteUser(UUID id) {
        User user = getUser(id);
        user.deletedAt = LocalDateTime.now(ZoneOffset.UTC);

        persist(user);
        flush();

        return user;
    }
}
