package com.tales.terra.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;

@QuarkusTest
public class UsersTest {
    @Inject
    TestFactory factory;

    @Inject
    Users users;

    @Nested
    @DisplayName("Get user")
    class GetUser {
        @Test
        @DisplayName("Returns the user")
        void returnsUser() {
            User user = factory.insertUser();
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
    @DisplayName("Fetch user")
    class FetchUser {
        @Test
        @DisplayName("Returns the user")
        void returnsUser() {
            User user = factory.insertUser();
            User result = users.fetchUser(user.id);

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
        void throwsExceptionWhenUserDoesNotExist() {
            UUID id = UUID.randomUUID();

            assertThrows(NotFoundException.class, () -> users.fetchUser(id));
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
            attrs.emailAddress = "jane.doe987@example.com";

            User result = users.createUser(attrs);

            assertEquals(attrs.firstName, result.firstName);
            assertEquals(attrs.lastName, result.lastName);
            assertEquals(attrs.emailAddress, result.emailAddress);
            assertEquals(null, result.deletedAt);
        }

        @Test
        @DisplayName("Throws an exception when the user already exists")
        void throwsExceptionWhenUserAlreadyExists() {
            User user = factory.insertUser();
            Users.CreateAttrs attrs = new Users.CreateAttrs();

            attrs.firstName = user.firstName;
            attrs.lastName = user.lastName;
            attrs.emailAddress = user.emailAddress;

            assertThrows(ConflictException.class, () -> users.createUser(attrs));
        }

        @Test
        @DisplayName("Throws an exception when the parameters are invalid")
        void throwsExceptionWhenParametersInvalid() {
            Users.CreateAttrs attrs = new Users.CreateAttrs();

            attrs.firstName = "";
            attrs.lastName = "";
            attrs.emailAddress = "";

            assertThrows(ConstraintViolationException.class, () -> users.createUser(attrs));
        }
    }

    @Nested
    @DisplayName("Update user")
    class UpdateUser {
        @Test
        @DisplayName("Returns the updated the user")
        void returnsUser() {
            User user = factory.insertUser();
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
            LocalDateTime timestamp = LocalDateTime.now();
            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("deletedAt", timestamp);

            User user = factory.insertUser(hashMap);
            Users.UpdateAttrs attrs = new Users.UpdateAttrs();

            attrs.firstName = "Janeth";
            attrs.lastName = "Doer";

            assertThrows(NotFoundException.class, () -> users.updateUser(user.id, attrs));
        }

        @Test
        @DisplayName("Throws an exception when the user does not exist")
        void throwsExceptionWhenUserDoesNotExist() {
            UUID id = UUID.randomUUID();
            Users.UpdateAttrs attrs = new Users.UpdateAttrs();

            attrs.firstName = "Janeth";
            attrs.lastName = "Doer";

            assertThrows(NotFoundException.class, () -> users.updateUser(id, attrs));
        }

        @Test
        @DisplayName("Throws an exception when the parameters are invalid")
        void throwsExceptionWhenParametersInvalid() {
            UUID id = UUID.randomUUID();
            Users.UpdateAttrs attrs = new Users.UpdateAttrs();

            attrs.firstName = "";
            attrs.lastName = "";

            assertThrows(ConstraintViolationException.class, () -> users.updateUser(id, attrs));
        }
    }

    @Nested
    @DisplayName("Delete user")
    class DeleteUser {
        @Test
        @DisplayName("Returns the deleted user")
        void returnsUser() {
            User user = factory.insertUser();
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
            LocalDateTime timestamp = LocalDateTime.now();
            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("deletedAt", timestamp);

            User user = factory.insertUser(hashMap);

            assertThrows(NotFoundException.class, () -> users.deleteUser(user.id));
        }

        @Test
        @DisplayName("Throws an exception when the user does not exist")
        void throwsExceptionWhenUserDoesNotExist() {
            UUID id = UUID.randomUUID();

            assertThrows(NotFoundException.class, () -> users.deleteUser(id));
        }
    }
}
