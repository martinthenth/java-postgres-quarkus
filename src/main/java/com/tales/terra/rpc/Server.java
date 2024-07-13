package com.tales.terra.rpc;

import com.tales.terra.rpc.v1.RpcService;
import com.tales.terra.rpc.v1.get_user.v1.GetUserRequest;
import com.tales.terra.rpc.v1.get_user.v1.GetUserResponse;
import com.tales.terra.rpc.v1.create_user.v1.CreateUserRequest;
import com.tales.terra.rpc.v1.create_user.v1.CreateUserResponse;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@GrpcService
public class Server implements RpcService {
    @Inject
    UserHandler userHandler;

    @Override
    @Transactional
    public Uni<GetUserResponse> getUser(GetUserRequest request) {
        return userHandler.getUser(request);
    }

    @Override
    @Transactional
    public Uni<CreateUserResponse> createUser(CreateUserRequest request) {
        return userHandler.createUser(request);
    }
}
