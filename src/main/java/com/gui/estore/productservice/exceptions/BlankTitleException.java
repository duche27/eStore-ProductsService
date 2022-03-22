package com.gui.estore.productservice.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BlankTitleException extends RuntimeException {

    private static final long serialVersionUID = -5711003372917579047L;

    public BlankTitleException(String errorMessage) {
        super(errorMessage);
    }
}
