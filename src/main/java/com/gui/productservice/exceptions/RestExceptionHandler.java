package com.gui.productservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PriceLowerThanZeroException.class)
    public ResponseEntity<Error> invalidPriceErrorHandler(PriceLowerThanZeroException e) {

        Error error = new Error(e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(PriceLowerThanZeroException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public @ResponseBody Error invalidPriceErrorHandler(PriceLowerThanZeroException e) {
//
//        return new Error(e.getMessage(), ZonedDateTime.now());
//    }

    @ExceptionHandler
    public ResponseEntity<Error> noDataErrorHandler(NoDataFoundException e) {

        Error error = new Error(e.getMessage(), ZonedDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
    }

//    @ExceptionHandler(PriceLowerThanZeroException.class)
//    public ResponseEntity<List> validationErrorHandler(ConstraintViolationException e) {
//
//        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
//
//        e.getConstraintViolations().forEach(c -> {
//            errors.add(c.getPropertyPath() + " : " + c.getMessage());
//        });
//
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
}
