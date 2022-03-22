package com.gui.estore.productservice.exceptions;

public class PriceLowerThanZeroException extends RuntimeException {

    private static final long serialVersionUID = -6927194635912846842L;

    public PriceLowerThanZeroException(String errorMessage) {
        super(errorMessage);
    }
}
