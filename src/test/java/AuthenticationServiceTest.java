package com.campusflow;

import com.campusflow.exception.AuthenticationException;
import com.campusflow.model.User;
import com.campusflow.service.AuthenticationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {

    private final AuthenticationService authenticationService =
            new AuthenticationService();

    @Test
    void shouldRejectBlankEmail() {

        assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.login(
                        "",
                        "Admin@123"
                )
        );
    }

    @Test
    void shouldRejectInvalidEmailFormat() {

        assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.login(
                        "invalid-email",
                        "Admin@123"
                )
        );
    }

    @Test
    void shouldRejectBlankPassword() {

        assertThrows(
                IllegalArgumentException.class,
                () -> authenticationService.login(
                        "admin@campusflow.com",
                        ""
                )
        );
    }

    @Test
    void shouldRejectUnknownUser() {

        assertThrows(
                AuthenticationException.class,
                () -> authenticationService.login(
                        "doesnotexist@campusflow.com",
                        "Admin@123"
                )
        );
    }

    @Test
    void shouldRejectIncorrectPassword() {

        assertThrows(
                AuthenticationException.class,
                () -> authenticationService.login(
                        "admin@campusflow.com",
                        "WrongPassword"
                )
        );
    }

    @Test
    void shouldAuthenticateValidUser() {

        User user = authenticationService.login(
                "admin@campusflow.com",
                "Admin@123"
        );

        assertNotNull(user);
        assertEquals(
                "admin@campusflow.com",
                user.getEmail()
        );
        assertEquals(
                "ADMIN",
                user.getRole()
        );
    }
}