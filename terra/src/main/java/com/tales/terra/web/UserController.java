package com.tales.terra.web;

import com.tales.terra.core.User;
import com.tales.terra.core.Users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    /**
     * The CreateParams class defines parameters for the create user endpoint.
     */
    public static class CreateParams {
        @NotBlank
        public String firstName;
        @NotBlank
        public String lastName;
    }

    /**
     * The UpdateParams class defines parameters for the update user endpoint.
     */
    public static class UpdateParams {
        @NotBlank
        public String firstName;
        @NotBlank
        public String lastName;
    }

    /**
     * Show a user.
     * This method handles GET requests to retrieve user information.
     * 
     * @return a User object
     */
    @GET
    @Path("{id}")
    public User show(@NotNull String id) {
        return Users.getUser(id);
    }

    /**
     * Create a user.
     * This method handles POST requests to create a new user.
     * 
     * @return a User object
     */
    @POST
    public User create(@Valid @NotNull CreateParams params) {
        // System.out.println(params);
        // System.out.println(params.firstName);
        // System.out.println(params.lastName);

        // TODO: `null` passes with `200` status code

        return Users.createUser();
    }

    /**
     * Update a user.
     * This method handles PATCH requests to update user information.
     * 
     * @return a User object
     */
    @PATCH
    @Path("{id}")
    public User update(@NotNull String id, @Valid @NotNull UpdateParams params) {
        // System.out.println(id);
        // System.out.println(params);

        return Users.updateUser(id);
    }

    /**
     * Delete a user.
     * This method handles DELETE requests to remove a user.
     * 
     * @return a User object
     */
    @DELETE
    @Path("{id}")
    public User delete(@NotNull String id) {
        return Users.deleteUser(id);
    }
}
