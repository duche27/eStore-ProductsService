package com.gui.productservice.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class PriceLowerThanZeroException extends RuntimeException {

    private static final long serialVersionUID = -6927194635912846842L;

    public PriceLowerThanZeroException(String errorMessage) {
        super(errorMessage);
    }
}
