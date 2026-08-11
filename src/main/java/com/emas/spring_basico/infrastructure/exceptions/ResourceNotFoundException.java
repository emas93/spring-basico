package com.emas.spring_basico.infrastructure.exceptions;

import jakarta.transaction.Transactional;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {

        super(message);
    }

    public ResourceNotFoundException (String message,Throwable throwable){
        super(message,throwable);
    }


}
