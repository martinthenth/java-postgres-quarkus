package com.tales.terra;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.tales.terra.core.User;
import com.tales.terra.core.Users;
import com.tales.terra.web.UserController;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
class UserControllerTest {
    @Inject
    private UserController controller;

    @Inject
    private Users users;

    @Nested
    @DisplayName("Show")
    class Show {
        @Test
        @DisplayName("Shows the user")
        void show() {
            Users.CreateAttrs attrs = new Users.CreateAttrs();
            attrs.firstName = "Jane";
            attrs.lastName = "Doe";
            attrs.emailAddress = "jane.doe@example.com";

            User user = users.createUser(attrs);
            User result = controller.show(user.id);

            assertEquals(user.id, result.id);
        }

        @Test
        @DisplayName("Shows the user (integration)")
        void showIntegration() {
            Users.CreateAttrs attrs = new Users.CreateAttrs();
            attrs.firstName = "Jane";
            attrs.lastName = "Doe";
            attrs.emailAddress = "jane.doe@example.com";

            User user = users.createUser(attrs);

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .pathParam("id", user.id)
                    .when().get("/user/{id}")
                    .then()
                    .statusCode(200)
                    .body("id", is(user.id.toString()))
                    .body("firstName", is(user.firstName))
                    .body("lastName", is(user.lastName))
                    .body("emailAddress", is(user.emailAddress))
                    .body("createdAt", is(user.createdAt.toString()))
                    .body("updatedAt", is(user.updatedAt.toString()))
                    .body("deletedAt", nullValue());

        }
    }

    @Nested
    @DisplayName("Create")
    class Create {
        @Test
        @DisplayName("Creates a user")
        void createsUser() {
            UserController.CreateParams params = new UserController.CreateParams();
            params.firstName = "Jane";
            params.lastName = "Doe";
            params.emailAddress = "jane.doe@example.com";

            User result = controller.create(params);

            assertEquals(params.firstName, result.firstName);
            assertEquals(params.lastName, result.lastName);
        }

        @Test
        @DisplayName("Creates a user (integration)")
        void createsUserIntegration() {
            UserController.CreateParams params = new UserController.CreateParams();
            params.firstName = "Jane";
            params.lastName = "Doe";
            params.emailAddress = "jane.doe@example.com";

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(params)
                    .when()
                    .post("/user")
                    .then()
                    .statusCode(200)
                    .body("firstName", is(params.firstName))
                    .body("lastName", is(params.lastName))
                    .body("emailAddress", is(params.emailAddress))
                    .body("deletedAt", nullValue());
        }

        @Test
        @DisplayName("Invalid parameters (integration)")
        void invalidParametersIntegration() {
            UserController.CreateParams params = new UserController.CreateParams();
            params.firstName = "";
            params.lastName = "";
            params.emailAddress = "";

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(params)
                    .when()
                    .post("/user")
                    .then()
                    .statusCode(400)
                    .body("title", is("Constraint Violation"))
                    .body("status", is(400))
                    .rootPath("violations.find { it.field == 'create.params.firstName' }")
                    .body("message", is("must not be blank"))
                    .rootPath("violations.find { it.field == 'create.params.lastName' }")
                    .body("message", is("must not be blank"))
                    .rootPath("violations.find { it.field == 'create.params.emailAddress' }")
                    .body("message", is("must not be blank"));
        }

        @Test
        @DisplayName("Missing parameters (integration)")
        void missingParametersIntegration() {
            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .when()
                    .post("/user")
                    .then()
                    .statusCode(400)
                    .body("title", is("Constraint Violation"))
                    .body("status", is(400))
                    .body("violations[0].field", is("create.params"))
                    .body("violations[0].message", is("must not be null"));
        }
    }

    @Nested
    @DisplayName("Update")
    class Update {
        @Test
        @DisplayName("Updates the user")
        void updatesUser() {
            Users.CreateAttrs attrs = new Users.CreateAttrs();
            attrs.firstName = "Jane";
            attrs.lastName = "Doe";
            attrs.emailAddress = "jane.doe@example.com";

            User user = users.createUser(attrs);
            UserController.UpdateParams params = new UserController.UpdateParams();
            params.firstName = "Janeth";
            params.lastName = "Doer";

            User result = controller.update(user.id, params);

            assertEquals(user.id, result.id);
            assertEquals(params.firstName, result.firstName);
            assertEquals(params.lastName, result.lastName);
        }

        @Test
        @DisplayName("Updates the user (integration)")
        void updatesUserIntegration() {
            Users.CreateAttrs attrs = new Users.CreateAttrs();
            attrs.firstName = "Jane";
            attrs.lastName = "Doe";
            attrs.emailAddress = "jane.doe@example.com";

            User user = users.createUser(attrs);
            UserController.UpdateParams params = new UserController.UpdateParams();
            params.firstName = "Janeth";
            params.lastName = "Doer";

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .pathParam("id", user.id)
                    .body(params)
                    .when()
                    .patch("/user/{id}")
                    .then()
                    .statusCode(200)
                    .body("id", is(user.id.toString()))
                    .body("firstName", is(params.firstName))
                    .body("lastName", is(params.lastName))
                    .body("emailAddress", is(user.emailAddress))
                    .body("createdAt", is(user.createdAt.toString()))
                    .body("updatedAt", not(is(user.updatedAt.toString())))
                    .body("deletedAt", nullValue());
        }

        @Test
        @DisplayName("Invalid parameters (integration)")
        void invalidParametersIntegration() {
            Users.CreateAttrs attrs = new Users.CreateAttrs();
            attrs.firstName = "Jane";
            attrs.lastName = "Doe";
            attrs.emailAddress = "jane.doe@example.com";

            User user = users.createUser(attrs);
            UserController.UpdateParams params = new UserController.UpdateParams();
            params.firstName = "";
            params.lastName = "";

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .pathParam("id", user.id)
                    .body(params)
                    .when()
                    .patch("/user/{id}")
                    .then()
                    .statusCode(400)
                    .body("title", is("Constraint Violation"))
                    .body("status", is(400))
                    .rootPath("violations.find { it.field == 'update.params.firstName' }")
                    .body("message", is("must not be blank"))
                    .rootPath("violations.find { it.field == 'update.params.lastName' }")
                    .body("message", is("must not be blank"));
        }

        @Test
        @DisplayName("Missing parameters (integration)")
        void missingParametersIntegration() {
            Users.CreateAttrs attrs = new Users.CreateAttrs();
            attrs.firstName = "Jane";
            attrs.lastName = "Doe";
            attrs.emailAddress = "jane.doe@example.com";

            User user = users.createUser(attrs);

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .pathParam("id", user.id)
                    .when()
                    .patch("/user/{id}")
                    .then()
                    .statusCode(400)
                    .body("title", is("Constraint Violation"))
                    .body("status", is(400))
                    .body("violations[0].field", is("update.params"))
                    .body("violations[0].message", is("must not be null"));
        }
    }

    @Nested
    @DisplayName("Delete")
    class Delete {
        @Test
        @DisplayName("Deletes the user")
        void delete() {
            Users.CreateAttrs attrs = new Users.CreateAttrs();
            attrs.firstName = "Jane";
            attrs.lastName = "Doe";
            attrs.emailAddress = "jane.doe@example.com";

            User user = users.createUser(attrs);
            User result = controller.delete(user.id);

            assertEquals(user.id, result.id);
            assertNotEquals(null, result.deletedAt);
        }

        @Test
        @DisplayName("Deletes the user (integration)")
        void deleteIntegration() {
            Users.CreateAttrs attrs = new Users.CreateAttrs();
            attrs.firstName = "Jane";
            attrs.lastName = "Doe";
            attrs.emailAddress = "jane.doe@example.com";

            User user = users.createUser(attrs);

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .pathParam("id", user.id)
                    .when().delete("/user/{id}")
                    .then()
                    .statusCode(200)
                    .body("id", is(user.id.toString()))
                    .body("firstName", is(user.firstName))
                    .body("lastName", is(user.lastName))
                    .body("emailAddress", is(user.emailAddress))
                    .body("createdAt", is(user.createdAt.toString()))
                    .body("updatedAt", not(is(user.updatedAt.toString())))
                    .body("deletedAt", is(not(nullValue())));
        }
    }
}