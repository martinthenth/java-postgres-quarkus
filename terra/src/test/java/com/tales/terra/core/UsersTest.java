package com.tales.terra.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class UsersTest {
    @Inject
    Users users;

    @Transactional
    User insertUser() {
        Users.CreateAttrs attrs = new Users.CreateAttrs();

        attrs.firstName = "Jane";
        attrs.lastName = "Doe";
        attrs.emailAddress = "jane.doe@example.com";

        return users.createUser(attrs);
    }

    @Nested
    @DisplayName("Get user")
    class GetUser {
        @Test
        @DisplayName("Get a user")
        void getsUser() {
            User user = insertUser();
            User result = users.getUser(user.id);

            assertEquals(user.id, result.id);
            assertEquals(user.firstName, result.firstName);
            assertEquals(user.lastName, result.lastName);
            assertEquals(user.emailAddress, result.emailAddress);
            assertEquals(user.createdAt, result.createdAt);
            assertEquals(user.updatedAt, result.updatedAt);
            assertEquals(user.deletedAt, result.deletedAt);
        }
    }

    @Nested
    @DisplayName("Create user")
    class CreateUser {
        @Test
        @DisplayName("Creates a user")
        void createsUser() {
            Users.CreateAttrs attrs = new Users.CreateAttrs();

            attrs.firstName = "Jane";
            attrs.lastName = "Doe";
            attrs.emailAddress = "jane.doe@example.com";

            User result = users.createUser(attrs);

            assertEquals(attrs.firstName, result.firstName);
            assertEquals(attrs.lastName, result.lastName);
            assertEquals(attrs.emailAddress, result.emailAddress);
            assertEquals(null, result.deletedAt);
        }
    }

    @Nested
    @DisplayName("Update user")
    class UpdateUser {
        @Test
        @DisplayName("Updates a user")
        void updatesUser() {
            User user = insertUser();
            Users.UpdateAttrs attrs = new Users.UpdateAttrs();

            attrs.firstName = "Janeth";
            attrs.lastName = "Doer";

            User result = users.updateUser(user.id, attrs);

            assertEquals(user.id, result.id);
            assertEquals(attrs.firstName, result.firstName);
            assertEquals(attrs.lastName, result.lastName);
            assertEquals(user.emailAddress, result.emailAddress);
            assertEquals(user.createdAt, result.createdAt);
            assertNotEquals(user.updatedAt, result.updatedAt);
            assertEquals(user.deletedAt, result.deletedAt);
        }
    }

    @Nested
    @DisplayName("Delete user")
    class DeleteUser {
        @Test
        @DisplayName("Deletes a user")
        void deletesUser() {
            User user = insertUser();
            User result = users.deleteUser(user.id);

            assertEquals(user.id, result.id);
            assertEquals(user.firstName, result.firstName);
            assertEquals(user.lastName, result.lastName);
            assertEquals(user.emailAddress, result.emailAddress);
            assertEquals(user.createdAt, result.createdAt);
            assertNotEquals(user.updatedAt, result.updatedAt);
            assertNotEquals(null, result.deletedAt);
        }
    }
}
