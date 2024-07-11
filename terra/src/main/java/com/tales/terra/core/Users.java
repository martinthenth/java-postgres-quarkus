package com.tales.terra.core;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import com.github.f4b6a3.uuid.UuidCreator;

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
     * @return a User object
     */
    public User getUser(UUID id) {
        return findById(id);
    }

    /**
     * Creates a user with the attributes.
     * 
     * @param attrs
     * @return a User object
     */
    @Transactional
    public User createUser(CreateAttrs attrs) {
        UUID id = UuidCreator.getTimeOrderedEpoch();
        LocalDateTime timestamp = LocalDateTime.now(ZoneOffset.UTC);
        User user = new User();

        user.id = id;
        user.firstName = attrs.firstName;
        user.lastName = attrs.lastName;
        user.emailAddress = attrs.emailAddress;
        user.createdAt = timestamp;
        user.updatedAt = timestamp;
        user.deletedAt = null;

        persist(user);

        return user;
    }

    /**
     * Updates the user by id and with the attributes.
     * 
     * @param id
     * @param attrs
     * @return a User object
     */
    @Transactional
    public User updateUser(UUID id, UpdateAttrs attrs) {
        User user = getUser(id);

        if (user == null)
            throw new RuntimeException("Not found");
        if (user.deletedAt != null)
            throw new RuntimeException("Is deleted");

        LocalDateTime timestamp = LocalDateTime.now(ZoneOffset.UTC);

        user.firstName = attrs.firstName;
        user.lastName = attrs.lastName;
        user.updatedAt = timestamp;

        persist(user);

        return user;
    }

    /**
     * Deletes the user by id.
     * 
     * @param id
     * @return a User object
     */
    @Transactional
    public User deleteUser(UUID id) {
        User user = getUser(id);

        if (user == null)
            throw new RuntimeException("Not found");
        if (user.deletedAt != null)
            throw new RuntimeException("Already deleted");

        LocalDateTime timestamp = LocalDateTime.now(ZoneOffset.UTC);

        user.updatedAt = timestamp;
        user.deletedAt = timestamp;

        persist(user);

        return user;
    }
}
