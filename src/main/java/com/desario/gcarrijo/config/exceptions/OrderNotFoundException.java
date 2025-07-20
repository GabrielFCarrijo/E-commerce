package com.desario.gcarrijo.config.exceptions;

import java.util.UUID;

public class OrderNotFoundException extends EntityNotFoundException {
    public OrderNotFoundException (UUID id) {
        super("Order",id);
    }

    public OrderNotFoundException (String message) {
        super("Order",message);
    }
}