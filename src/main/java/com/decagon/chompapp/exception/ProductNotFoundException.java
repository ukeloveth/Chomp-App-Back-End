package com.decagon.chompapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
public class ProductNotFoundException extends RuntimeException{

    private String productName;
    private String fieldName;
    private Long fieldValue;

    public ProductNotFoundException(String productName, String fieldName, Long fieldValue) {

        super(String.format("%s not found with %s : '%s' ", productName, fieldName, fieldValue));
        this.productName = productName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
