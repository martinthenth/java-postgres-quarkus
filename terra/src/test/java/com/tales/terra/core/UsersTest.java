package com.tales.terra.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

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
        @DisplayName("Returns the user")
        void returnsUser() {
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

        @Test
        @DisplayName("Returns a null when the user does not exist")
        void returnsNullWhenUserDoesNotExist() {
            UUID id = UUID.randomUUID();
            User result = users.getUser(id);

            assertEquals(null, result);
        }
    }

    @Nested
    @DisplayName("Create user")
    class CreateUser {
        @Test
        @DisplayName("Returns the created user")
        void returnsUser() {
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

        @Test
        @DisplayName("Throws an exception when the user already exists")
        void throwsExceptionWhenUserAlreadyExists() {
            assertEquals(true, false);
        }
    }

    @Nested
    @DisplayName("Update user")
    class UpdateUser {
        @Test
        @DisplayName("Returns the updated the user")
        void returnsUser() {
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

        @Test
        @DisplayName("Throws an exception when the user is deleted")
        void throwsExceptionWhenUserIsDeleted() {
            assertEquals(true, false);
        }

        @Test
        @DisplayName("Throws an exception when the user does not exist")
        void throwsExceptionWhenUserDoesNotExist() {
            UUID id = UUID.randomUUID();
            Users.UpdateAttrs attrs = new Users.UpdateAttrs();

            assertThrows(RuntimeException.class, () -> users.updateUser(id, attrs));
        }
    }

    @Nested
    @DisplayName("Delete user")
    class DeleteUser {
        @Test
        @DisplayName("Returns the deleted user")
        void returnsUser() {
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

        @Test
        @DisplayName("Throws an exception when the user is deleted")
        void throwsExceptionWhenUserIsDeleted() {
            assertEquals(true, false);
        }

        @Test
        @DisplayName("Throws an exception when the user does not exist")
        void throwsExceptionWhenUserDoesNotExist() {
            UUID id = UUID.randomUUID();

            assertThrows(RuntimeException.class, () -> users.deleteUser(id));
        }
    }
}
