package com.tales.terra.rpc;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.tales.terra.core.TestFactory;
import com.tales.terra.core.User;
import com.tales.terra.rpc.v1.RpcService;
import com.tales.terra.rpc.v1.create_user.v1.CreateUserRequest;
import com.tales.terra.rpc.v1.create_user.v1.CreateUserResponse;
import com.tales.terra.rpc.v1.get_user.v1.GetUserRequest;
import com.tales.terra.rpc.v1.get_user.v1.GetUserResponse;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@QuarkusTest
class UserHandlerTest {
    @Inject
    TestFactory factory;

    @GrpcClient
    RpcService client;

    @Nested
    @DisplayName("Get user")
    class GetUser {
        @Test
        @DisplayName("Returns the user")
        void returnsUser() {
            User user = factory.insertUser();
            CompletableFuture<GetUserResponse> message = new CompletableFuture<>();

            client.getUser(
                    GetUserRequest
                            .newBuilder()
                            .setId(user.id.toString())
                            .build())
                    .subscribe()
                    .with(reply -> message.complete(reply));

            GetUserResponse response = assertDoesNotThrow(() -> message.get(1, TimeUnit.SECONDS));

            assertEquals(user.id.toString(), response.getUser().getId());
            assertEquals(user.firstName, response.getUser().getFirstName());
            assertEquals(user.lastName, response.getUser().getLastName());
            assertEquals(user.emailAddress, response.getUser().getEmailAddress());
            assertEquals(user.createdAt.toString(), response.getUser().getCreatedAt());
            assertEquals(user.updatedAt.toString(), response.getUser().getUpdatedAt());
            assertEquals("", response.getUser().getDeletedAt());
        }

        @Test
        @DisplayName("Renders an error when the user does not exist")
        void rendersErrorWhenUserDoesNotExist() {
            UUID id = UUID.randomUUID();
            CompletableFuture<GetUserResponse> message = new CompletableFuture<>();

            client.getUser(
                    GetUserRequest
                            .newBuilder()
                            .setId(id.toString())
                            .build())
                    .subscribe()
                    .with(reply -> message.complete(reply), throwable -> message.completeExceptionally(throwable));

            ExecutionException exception = assertThrows(ExecutionException.class,
                    () -> message.get(1, TimeUnit.SECONDS));

            assertEquals(Status.NOT_FOUND, ((StatusRuntimeException) exception.getCause()).getStatus());
        }
    }

    @Nested
    @DisplayName("Create user")
    class CreateUser {
        @Test
        @DisplayName("Returns the user")
        void returnsUser() {
            CompletableFuture<CreateUserResponse> message = new CompletableFuture<>();

            client.createUser(
                    CreateUserRequest
                            .newBuilder()
                            .setFirstName("Jane")
                            .setLastName("Doe")
                            .setEmailAddress("jane.doe@example.com")
                            .build())
                    .subscribe()
                    .with(reply -> message.complete(reply));

            CreateUserResponse response = assertDoesNotThrow(() -> message.get(1, TimeUnit.SECONDS));

            assertEquals("Jane", response.getUser().getFirstName());
            assertEquals("Doe", response.getUser().getLastName());
            assertEquals("jane.doe@example.com", response.getUser().getEmailAddress());
        }

        @Test
        @DisplayName("Renders an error when the user already exists")
        void rendersErrorWhenUserAlreadyExists() {
            User user = factory.insertUser();
            CompletableFuture<CreateUserResponse> message = new CompletableFuture<>();

            client.createUser(
                    CreateUserRequest
                            .newBuilder()
                            .setFirstName(user.firstName)
                            .setLastName(user.lastName)
                            .setEmailAddress(user.emailAddress)
                            .build())
                    .subscribe()
                    .with(reply -> message.complete(reply), throwable -> message.completeExceptionally(throwable));

            ExecutionException exception = assertThrows(ExecutionException.class,
                    () -> message.get(1, TimeUnit.SECONDS));

            assertEquals(Status.ALREADY_EXISTS, ((StatusRuntimeException) exception.getCause()).getStatus());
        }

        @Test
        @DisplayName("Renders an error when the parameters are invalid")
        void rendersErrorWhenParametersInvalid() {
            CompletableFuture<CreateUserResponse> message = new CompletableFuture<>();

            client.createUser(
                    CreateUserRequest
                            .newBuilder()
                            .setFirstName("")
                            .setLastName("")
                            .setEmailAddress("")
                            .build())
                    .subscribe()
                    .with(reply -> message.complete(reply), throwable -> message.completeExceptionally(throwable));

            ExecutionException exception = assertThrows(ExecutionException.class,
                    () -> message.get(1, TimeUnit.SECONDS));

            assertEquals(Status.INVALID_ARGUMENT, ((StatusRuntimeException) exception.getCause()).getStatus());
        }
    }
}
