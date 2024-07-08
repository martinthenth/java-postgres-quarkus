package com.tales.terra.web;

import com.tales.terra.core.User;
import com.tales.terra.rpc.v1.RpcService;
import com.tales.terra.rpc.v1.get_user.v1.GetUserRequest;
import com.tales.terra.rpc.v1.get_user.v1.GetUserResponse;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * The UserController class provides RESTful endpoints to manage users.
 * It supports operations to show, create, update, and delete a user.
 */
@Path("/user")
public class UserController {
    @GrpcClient
    RpcService terra;

    /**
     * Show a user.
     * This method handles GET requests to retrieve user information.
     * 
     * @return a User object
     */
    @GET
    public Uni<String> show() {
        String name = "Jane";

        return terra.getUser(GetUserRequest.newBuilder().setName(name).build())
                .onItem().transform(GetUserResponse::getMessage);
    }

    /**
     * Create a user.
     * This method handles POST requests to create a new user.
     * 
     * @return a User object
     */
    @POST
    public User create() {
        return new User("Martin", "Nijboer");
    }

    /**
     * Update a user.
     * This method handles PATCH requests to update user information.
     * 
     * @return a User object
     */
    @PATCH
    public User update() {
        return new User();
    }

    /**
     * Delete a user.
     * This method handles DELETE requests to remove a user.
     * 
     * @return a User object
     */
    @DELETE
    public User delete() {
        return new User();
    }
}
