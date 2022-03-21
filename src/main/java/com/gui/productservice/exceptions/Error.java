package com.gui.productservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {

    private String message;
    private ZonedDateTime timestamp;

    public Error(String message) {
        this.message = message;
        this.timestamp = ZonedDateTime.now();
    }
}