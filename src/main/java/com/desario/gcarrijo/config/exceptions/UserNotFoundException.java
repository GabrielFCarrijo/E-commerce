package com.desario.gcarrijo.config.exceptions;

import java.util.UUID;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String email) {
        super("User", email);
    }

    public UserNotFoundException(UUID id) {
        super("User", id);
    }
}
