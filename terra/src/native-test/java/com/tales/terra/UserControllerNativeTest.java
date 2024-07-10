package com.tales.terra;

import com.tales.terra.core.Users;
import com.tales.terra.web.UserController;

import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
class UserControllerNativeTest extends UserControllerTest {
    UserController controller;
    Users users;

    UserControllerNativeTest(UserController controller, Users users) {
        super(controller, users);
    }
}
