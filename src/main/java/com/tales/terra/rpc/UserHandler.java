package com.tales.terra.rpc;

import com.tales.terra.core.ConflictException;
import com.tales.terra.core.NotFoundException;
import com.tales.terra.core.User;
import com.tales.terra.core.Users;
import com.tales.terra.rpc.v1.get_user.v1.GetUserRequest;
import com.tales.terra.rpc.v1.get_user.v1.GetUserResponse;
import com.tales.terra.rpc.v1.create_user.v1.CreateUserRequest;
import com.tales.terra.rpc.v1.create_user.v1.CreateUserResponse;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;

import java.util.UUID;

@ApplicationScoped
public class UserHandler {
    @Inject
    Users users;

    public Uni<GetUserResponse> getUser(GetUserRequest request) {
        UUID id = UUID.fromString(request.getId());

        try {
            User user = users.fetchUser(id);

            return Uni.createFrom().item(() -> GetUserResponse
                    .newBuilder()
                    .setUser(com.tales.terra.shared.v1.user.v1.User
                            .newBuilder()
                            .setId(user.id.toString())
                            .setFirstName(user.firstName)
                            .setLastName(user.lastName)
                            .setEmailAddress(user.emailAddress)
                            .setCreatedAt(user.createdAt.toString())
                            .setUpdatedAt(user.updatedAt.toString())
                            .setDeletedAt((user.deletedAt == null) ? ""
                                    : user.deletedAt.toString())
                            .build())
                    .build());
        } catch (NotFoundException e) {
            throw new StatusRuntimeException(Status.NOT_FOUND);
        }
    }

    public Uni<CreateUserResponse> createUser(CreateUserRequest request) {
        Users.CreateAttrs attrs = new Users.CreateAttrs();

        attrs.firstName = request.getFirstName();
        attrs.lastName = request.getLastName();
        attrs.emailAddress = request.getEmailAddress();

        try {
            User user = users.createUser(attrs);

            return Uni.createFrom().item(() -> CreateUserResponse
                    .newBuilder()
                    .setUser(com.tales.terra.shared.v1.user.v1.User
                            .newBuilder()
                            .setId(user.id.toString())
                            .setFirstName(user.firstName)
                            .setLastName(user.lastName)
                            .setEmailAddress(user.emailAddress)
                            .setCreatedAt(user.createdAt.toString())
                            .setUpdatedAt(user.updatedAt.toString())
                            .setDeletedAt((user.deletedAt == null) ? ""
                                    : user.deletedAt.toString())
                            .build())
                    .build());
        } catch (ConflictException e) {
            throw new StatusRuntimeException(Status.ALREADY_EXISTS);
        } catch (ConstraintViolationException e) {
            throw new StatusRuntimeException(Status.INVALID_ARGUMENT);
        }
    }
}
