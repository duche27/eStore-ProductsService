package com.gui.productservice.commands.rest;

import com.gui.productservice.commands.CreateProductCommand;
import com.gui.productservice.exceptions.PriceLowerThanZeroException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public ProductCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("newProduct")
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductRestModel createProductRestModel) {

        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .price(createProductRestModel.getPrice())
                .quantity(createProductRestModel.getQuantity())
                .title(createProductRestModel.getTitle())
                .build();

        // WAIT devuelve un objeto future sin esperar respuesta - SENDandWAIT espera respuesta
        // lo mandamos a nuestro AGGREGATE que tiene el COMMANDHANDLER
        // pasa por el INTERCEPTOR
        try {
            commandGateway.sendAndWait(createProductCommand);
        } catch (Exception e) {
            log.error("[createProduct] error al enviar el command de creación de producto");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("lalala POST de " + createProductRestModel.getTitle() + " con id " + createProductCommand.getProductId(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct() {

        return ResponseEntity.ok("product editado");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct() {

        return ResponseEntity.ok("product eliminado");
    }
}
