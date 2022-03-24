package com.gui.estore.productservice.exceptions;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ProductErrorHandler {

    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity<ErrorMessage> handleIllegalStateException(IllegalStateException e) {

        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(value = {RuntimeException.class})
//    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException e) {
//
//        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
//        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
//    }

    // excepciones del AGGREGATE
    @ExceptionHandler(value = {CommandExecutionException.class})
    public ResponseEntity<ErrorMessage> handleCommandExecutionException(CommandExecutionException e, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> handleOtherException(Exception e, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorMessage> noDataErrorHandler(NoDataFoundException e) {

        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());

        return new ResponseEntity<>(errorMessage, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ErrorMessage> outOfStockErrorHandler(OutOfStockException e) {

        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(PriceLowerThanZeroException.class)
    public ResponseEntity<ErrorMessage> invalidPriceErrorHandler(PriceLowerThanZeroException e) {

        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BlankTitleException.class)
    public ResponseEntity<ErrorMessage> blankFieldErrorHandler(BlankTitleException e) {

        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
