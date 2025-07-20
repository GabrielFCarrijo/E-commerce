package com.desario.gcarrijo.config.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, Object identifier) {
        super(String.format("%s com identificador [%s] não foi encontrado.", entityName, identifier));
    }
}
