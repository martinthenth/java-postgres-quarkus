package com.tales.terra.web;

import com.tales.terra.core.ConflictException;
import com.tales.terra.core.NotFoundException;
import com.tales.terra.core.User;
import com.tales.terra.core.Users;

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
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

/**
 * The UserController class provides RESTful endpoints to manage users.
 * It supports operations to show, create, update, and delete a user.
 */
@Path("/user")
public class UserController {
    @Inject
    Users users;

    /** Parameters for creating a user. */
    public static class CreateParams {
        @NotBlank
        public String firstName;

        @NotBlank
        public String lastName;

        @NotBlank
        public String emailAddress;
    }

    /** Parameters for updating a user. */
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
     * @param id
     * @return a User object
     */
    @GET
    @Path("{id}")
    public User show(@NotNull UUID id) {
        try {
            return users.fetchUser(id);
        } catch (NotFoundException e) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
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
    public User create(@NotNull @Valid CreateParams params) {
        Users.CreateAttrs attrs = new Users.CreateAttrs();

        attrs.firstName = params.firstName;
        attrs.lastName = params.lastName;
        attrs.emailAddress = params.emailAddress;

        try {
            return users.createUser(attrs);
        } catch (ConflictException e) {
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
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
    public User update(@NotNull UUID id, @NotNull @Valid UpdateParams params) {
        Users.UpdateAttrs attrs = new Users.UpdateAttrs();

        attrs.firstName = params.firstName;
        attrs.lastName = params.lastName;

        try {
            return users.updateUser(id, attrs);
        } catch (NotFoundException e) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
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
    public User delete(@NotNull UUID id) {
        try {
            return users.deleteUser(id);
        } catch (NotFoundException e) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
