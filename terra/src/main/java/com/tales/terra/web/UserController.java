package com.tales.terra.web;

import com.tales.terra.core.User;
import com.tales.terra.core.User2;
import com.tales.terra.core.Users;
import com.tales.terra.core.Users2;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
    @Inject
    private Users2 users;

    /** Parameters for creating a user. */
    public static class CreateParams {
        @NotBlank
        public String firstName;
        @NotBlank
        public String lastName;
    }

    /** Parameters for updating a user. */
    public static class UpdateParams {
        @NotBlank
        public String firstName;
        @NotBlank
        public String lastName;
    }

    /**
     * Create a user.
     * This method handles POST requests to create a new user.
     * 
     * @param params
     * @return a User object
     */
    @POST
    @Transactional
    public User2 create(@NotNull @Valid CreateParams params) {
        Users2.CreateAttrs attrs = new Users2.CreateAttrs();
        attrs.firstName = params.firstName;
        attrs.lastName = params.lastName;

        return users.createUser(attrs);
    }

    /**
     * Show a user.
     * This method handles GET requests to retrieve user information.
     * 
     * @param id
     * @return a User object
     */
    @GET
    @Path("{id}")
    public User show(@NotNull String id) {
        return Users.getUser(id);
    }

    /**
     * Update a user.
     * This method handles PATCH requests to update user information.
     * 
     * @param id
     * @param params
     * @return a User object
     */
    @PATCH
    @Path("{id}")
    public User update(@NotNull String id, @NotNull @Valid UpdateParams params) {
        // System.out.println(id);
        // System.out.println(params);

        return Users.updateUser(id);
    }

    /**
     * Delete a user.
     * This method handles DELETE requests to remove a user.
     * 
     * @param id
     * @return a User object
     */
    @DELETE
    @Path("{id}")
    public User delete(@NotNull String id) {
        return Users.deleteUser(id);
    }
}
