package com.tales.terra;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        void showUser() {
            User user = new User();
            UserController controller = new UserController();
            User result = controller.show();

            assertEquals(user.id, result.id);
        }
    }

    @Nested
    @DisplayName("Create")
    class Create {
        @Test
        @DisplayName("Creates a user")
        void createUser() {
            assert false;
        }

        @Test
        @DisplayName("Creates a user (integration)")
        void createUserIntegration() {
            given()
                    .when().post("/user")
                    .then()
                    .statusCode(200)
                    .body(is("Hello from Quarkus REST"));
        }
    }

    @Nested
    @DisplayName("Update")
    class Update {
        @Test
        @DisplayName("Updates a user")
        void updateUser() {
            assert false;
        }
    }

    @Nested
    @DisplayName("Delete")
    class Delete {
        @Test
        @DisplayName("Deletes a user")
        void deleteUser() {
            User user = new User();
            UserController controller = new UserController();
            User result = controller.delete();

            assertEquals(user.id, result.id);
            assertNotEquals(user.deletedAt, null);
        }
    }
}