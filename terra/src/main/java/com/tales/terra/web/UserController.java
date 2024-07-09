package com.tales.terra.web;

import java.util.List;

import com.tales.terra.core.Greeting;
import com.tales.terra.core.Greetings;
import com.tales.terra.core.User;
import com.tales.terra.core.Users;

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
    public User create(@NotNull @Valid CreateParams params) {
        // System.out.println(params);
        // System.out.println(params.firstName);
        // System.out.println(params.lastName);

        // TODO: `null` passes with `200` status code

        // Greeting greeting = new Greeting();
        // greeting.name = "Martin";
        // greeting.persist();

        // System.out.println(greeting);

        // List<Greeting> greetings = Greeting.listAll();

        // System.out.println(greetings);

        Greeting greeting = new Greetings().createGreeting("Martin");

        System.out.println(greeting);
        System.out.println(greeting.name);

        return Users.createUser();
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
