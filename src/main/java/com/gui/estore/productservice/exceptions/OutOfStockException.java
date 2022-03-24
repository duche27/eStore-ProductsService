package com.gui.estore.productservice.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OutOfStockException extends RuntimeException {

    public OutOfStockException(String errorMessage) {
        super(errorMessage);
    }
}
