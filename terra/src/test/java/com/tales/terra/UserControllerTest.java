package com.tales.terra;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.tales.terra.core.User;
import com.tales.terra.web.UserController;

@QuarkusTest
class UserControllerTest {
    @Nested
    @DisplayName("Show")
    class Show {
        @Test
        @DisplayName("Shows a user")
        void show() {
            User user = new User();
            User result = new UserController().show("123");

            assertEquals(user.id, result.id);
        }

        @Test
        @DisplayName("Shows a user (integration)")
        void showIntegration() {
            User user = new User();

            given()
                    .pathParam("id", user.id)
                    .when().get("/user/{id}")
                    .then()
                    .statusCode(200)
                    .body(is("{" +
                            "\"id\":\"123\"," +
                            "\"firstName\":\"firstName\"," +
                            "\"lastName\":\"lastName\"," +
                            "\"emailAddress\":\"emailAddress.com\"," +
                            "\"createdAt\":\"now\"," +
                            "\"updatedAt\":\"now\"," +
                            "\"deletedAt\":null" +
                            "}"));
        }
    }

    @Nested
    @DisplayName("Create")
    class Create {
        @Test
        @DisplayName("Creates a user")
        void create() {
            User result = new UserController().create();

            assertEquals("firstName", result.firstName);
            assertEquals("lastName", result.lastName);
        }

        @Test
        @DisplayName("Creates a user (integration)")
        void createIntegration() {
            given()
                    .when().post("/user")
                    .then()
                    .statusCode(200)
                    .body(is("{" +
                            "\"id\":\"123\"," +
                            "\"firstName\":\"firstName\"," +
                            "\"lastName\":\"lastName\"," +
                            "\"emailAddress\":\"emailAddress.com\"," +
                            "\"createdAt\":\"now\"," +
                            "\"updatedAt\":\"now\"," +
                            "\"deletedAt\":null" +
                            "}"));
        }
    }

    @Nested
    @DisplayName("Update")
    class Update {
        @Test
        @DisplayName("Updates a user")
        void update() {
            User user = new User();
            User result = new UserController().update(user.id);

            assertEquals(user.id, result.id);
        }

        @Test
        @DisplayName("Updates a user (integration)")
        void updateIntegration() {
            User user = new User();

            given()
                    .pathParam("id", user.id)
                    .when().patch("/user/{id}")
                    .then()
                    .statusCode(200)
                    .body(is("{" +
                            "\"id\":\"123\"," +
                            "\"firstName\":\"firstName\"," +
                            "\"lastName\":\"lastName\"," +
                            "\"emailAddress\":\"emailAddress.com\"," +
                            "\"createdAt\":\"now\"," +
                            "\"updatedAt\":\"now\"," +
                            "\"deletedAt\":null" +
                            "}"));
        }
    }

    @Nested
    @DisplayName("Delete")
    class Delete {
        @Test
        @DisplayName("Deletes a user")
        void delete() {
            User user = new User();
            User result = new UserController().delete(user.id);

            assertEquals(user.id, result.id);
            assertEquals(user.deletedAt, null);
        }

        @Test
        @DisplayName("Deletes a user (integration)")
        void deleteIntegration() {
            User user = new User();

            given()
                    .pathParam("id", user.id)
                    .when().delete("/user/{id}")
                    .then()
                    .statusCode(200)
                    .body(is("{" +
                            "\"id\":\"123\"," +
                            "\"firstName\":\"firstName\"," +
                            "\"lastName\":\"lastName\"," +
                            "\"emailAddress\":\"emailAddress.com\"," +
                            "\"createdAt\":\"now\"," +
                            "\"updatedAt\":\"now\"," +
                            "\"deletedAt\":null" +
                            "}"));
        }
    }
}