package com.tales.terra;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.tales.terra.core.User;
import com.tales.terra.core.User2;
import com.tales.terra.web.UserController;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;

import java.util.HashMap;

@QuarkusTest
class UserControllerTest {
    @Nested
    @DisplayName("Show")
    class Show {
        @Test
        @DisplayName("Shows the user")
        void show() {
            User user = new User();
            User result = new UserController().show(user.id);

            assertEquals(user.id, result.id);
        }

        @Test
        @DisplayName("Shows the user (integration)")
        void showIntegration() {
            User user = new User();

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .pathParam("id", user.id)
                    .when().get("/user/{id}")
                    .then()
                    .statusCode(200)
                    .body("id", is("123"))
                    .body("firstName", is("firstName"))
                    .body("lastName", is("lastName"))
                    .body("emailAddress", is("emailAddress.com"))
                    .body("createdAt", is("now"))
                    .body("updatedAt", is("now"))
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

            User2 result = new UserController().create(params);

            assertEquals("firstName", result.firstName);
            assertEquals("lastName", result.lastName);
        }

        @Test
        @DisplayName("Creates a user (integration)")
        void createsUserIntegration() {
            HashMap<String, String> params = new HashMap<>();
            params.put("firstName", "Jane");
            params.put("lastName", "Doe");

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(params)
                    .when()
                    .post("/user")
                    .then()
                    .statusCode(200)
                    .body("firstName", is("Jane"))
                    .body("lastName", is("Doe"))
                    // .body("emailAddress", is("emailAddress.com"))
                    .body("deletedAt", nullValue());
        }

        @Test
        @DisplayName("Invalid parameters (integration)")
        void invalidParametersIntegration() {
            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{" +
                            "\"firstName\": \"\"," +
                            "\"lastName\": \"\"" +
                            "}")
                    .when()
                    .post("/user")
                    .then()
                    .statusCode(400)
                    .body("title", is("Constraint Violation"))
                    .body("status", is(400))
                    .rootPath("violations.find { it.field == 'create.params.firstName' }")
                    .body("message", is("must not be blank"))
                    .rootPath("violations.find { it.field == 'create.params.lastName' }")
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
            User user = new User();
            UserController.UpdateParams params = new UserController.UpdateParams();
            params.firstName = "Janeth";
            params.lastName = "Doer";

            User result = new UserController().update(user.id, params);

            assertEquals(user.id, result.id);
            assertEquals(user.firstName, result.firstName);
            assertEquals(user.lastName, result.lastName);
        }

        @Test
        @DisplayName("Updates the user (integration)")
        void updatesUserIntegration() {
            User user = new User();

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .pathParam("id", user.id)
                    .body("{" +
                            "\"firstName\": \"Janeth\"," +
                            "\"lastName\": \"Doer\"" +
                            "}")
                    .when()
                    .patch("/user/{id}")
                    .then()
                    .statusCode(200)
                    .body("id", is("123"))
                    .body("firstName", is("firstName"))
                    .body("lastName", is("lastName"))
                    .body("emailAddress", is("emailAddress.com"))
                    .body("createdAt", is("now"))
                    .body("updatedAt", is("now"))
                    .body("deletedAt", nullValue());
        }

        @Test
        @DisplayName("Invalid parameters (integration)")
        void invalidParametersIntegration() {
            User user = new User();

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .pathParam("id", user.id)
                    .body("{" +
                            "\"firstName\": \"\"," +
                            "\"lastName\": \"\"" +
                            "}")
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
            User user = new User();

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
            User user = new User();
            User result = new UserController().delete(user.id);

            assertEquals(user.id, result.id);
            assertEquals(user.deletedAt, null);
        }

        @Test
        @DisplayName("Deletes the user (integration)")
        void deleteIntegration() {
            User user = new User();

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .pathParam("id", user.id)
                    .when().delete("/user/{id}")
                    .then()
                    .statusCode(200)
                    .body("id", is("123"))
                    .body("firstName", is("firstName"))
                    .body("lastName", is("lastName"))
                    .body("emailAddress", is("emailAddress.com"))
                    .body("createdAt", is("now"))
                    .body("updatedAt", is("now"))
                    .body("deletedAt", nullValue());
        }
    }
}