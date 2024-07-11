package com.tales.terra.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.tales.terra.core.TestFactory;
import com.tales.terra.core.User;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
class UserControllerTest {
    @Inject
    TestFactory factory;

    @Inject
    UserController controller;

    @Nested
    @DisplayName("Create")
    class Create {
        @Test
        @DisplayName("Renders the created user")
        void rendersUser() {
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
        @DisplayName("Renders an error when the user already exists")
        void rendersErrorWhenUserAlreadyExists() {
            assertEquals(true, false);
        }

        @Test
        @DisplayName("Renders an error when the parameters are invalid")
        void rendersErrorWhenParametersInvalid() {
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
        @DisplayName("Renders an error when the parameters are missing")
        void rendersErrorWhenParametersMissing() {
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
    @DisplayName("Show")
    class Show {
        @Test
        @DisplayName("Renders the user")
        void rendersUser() {
            User user = factory.insertUser();

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

        @Test
        @DisplayName("Renders an error when the user does not exist")
        void rendersErrorWhenUserDoesNotExist() {
            UUID id = UUID.randomUUID();

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .pathParam("id", id)
                    .when().get("/user/{id}")
                    .then()
                    .statusCode(404);
        }
    }

    @Nested
    @DisplayName("Update")
    class Update {
        @Test
        @DisplayName("Renders the updated user")
        void rendersUser() {
            User user = factory.insertUser();
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
        @DisplayName("Renders an error when the user does not exist")
        void rendersErrorWhenUserDoesNotExist() {
            UUID id = UUID.randomUUID();
            UserController.UpdateParams params = new UserController.UpdateParams();

            params.firstName = "Janeth";
            params.lastName = "Doer";

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .pathParam("id", id)
                    .body(params)
                    .when()
                    .patch("/user/{id}")
                    .then()
                    .statusCode(404);
        }

        @Test
        @DisplayName("Renders an error when the parameters are invalid")
        void rendersErrorWhenParametersInvalid() {
            User user = factory.insertUser();
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
        @DisplayName("Renders an error when the parameters are missing")
        void rendersErrorWhenParametersMissing() {
            User user = factory.insertUser();

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
        @DisplayName("Renders the deleted user")
        void rendersUser() {
            User user = factory.insertUser();

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

        @Test
        @DisplayName("Renders an error when the user does not exist")
        void rendersErrorWhenUserDoesNotExist() {
            UUID id = UUID.randomUUID();

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .pathParam("id", id)
                    .when().delete("/user/{id}")
                    .then()
                    .statusCode(404);
        }
    }
}