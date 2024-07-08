package com.tales.terra.rpc;

import com.tales.terra.rpc.v1.RpcService;
import com.tales.terra.rpc.v1.get_user.v1.GetUserRequest;
import com.tales.terra.rpc.v1.get_user.v1.GetUserResponse;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

@GrpcService
public class Server implements RpcService {
    @Override
    public Uni<GetUserResponse> getUser(GetUserRequest request) {
        return Uni.createFrom()
                .item(() -> GetUserResponse.newBuilder().setMessage("Hello " + request.getName()).build());
    }
}
