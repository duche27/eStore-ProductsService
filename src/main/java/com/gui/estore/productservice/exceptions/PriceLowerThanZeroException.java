package com.gui.estore.productservice.exceptions;

public class PriceLowerThanZeroException extends RuntimeException {

    public PriceLowerThanZeroException(String errorMessage) {
        super(errorMessage);
    }
}
