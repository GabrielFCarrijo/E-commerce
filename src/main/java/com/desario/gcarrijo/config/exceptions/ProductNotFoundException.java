package com.desario.gcarrijo.config.exceptions;

import java.util.UUID;

public class ProductNotFoundException extends EntityNotFoundException {
    public ProductNotFoundException(UUID id) {
        super("Product", id);
    }
}