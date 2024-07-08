package com.tales.terra.web;

import com.tales.terra.core.User;

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
     * Show a user.
     * This method handles GET requests to retrieve user information.
     * 
     * @return a User object
     */
    @GET
    @Path("{id}")
    public User show(String id) {
        return new User();
    }

    /**
     * Create a user.
     * This method handles POST requests to create a new user.
     * 
     * @return a User object
     */
    @POST
    public User create() {
        // TODO: Take the parameters, and create a new user object (or change the
        // fields)
        return new User();
    }

    /**
     * Update a user.
     * This method handles PATCH requests to update user information.
     * 
     * @return a User object
     */
    @PATCH
    @Path("{id}")
    public User update(String id) {
        // TODO: Take the parameters, and create a new user object (or change the
        // fields)
        return new User();
    }

    /**
     * Delete a user.
     * This method handles DELETE requests to remove a user.
     * 
     * @return a User object
     */
    @DELETE
    @Path("{id}")
    public User delete(String id) {
        // TODO: Set the `deletedAt` field to a value `"now"`
        return new User();
    }
}
