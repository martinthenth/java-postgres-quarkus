package com.tales.terra.core;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class UsersTest {
    @Inject
    Users users;

    @Transactional
    User insertUser() {
        Users.CreateAttrs attrs = new Users.CreateAttrs();
        attrs.firstName = "Jane";
        attrs.lastName = "Doe";
        attrs.emailAddress = "jane.doe@example.com";

        return users.createUser(attrs);
    }
}
