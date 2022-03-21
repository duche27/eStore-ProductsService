package com.gui.productservice.rest;

import com.gui.productservice.commands.CreateProductCommand;
import com.gui.productservice.model.CreateProductRestModel;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductsController {

    private final Environment env;
    private final CommandGateway commandGateway;

    @Autowired
    public ProductsController(Environment env, CommandGateway commandGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRestModel createProductRestModel) {

        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .price(createProductRestModel.getPrice())
                .quantity(createProductRestModel.getQuantity())
                .title(createProductRestModel.getTitle())
                .build();

        // wait devuelve un objeto future sin esperar respuesta
        // espera respuesta
        // lo mandamos a nuestro aggregate que tiene el commandHandler
        try {
            commandGateway.sendAndWait(createProductCommand);
        } catch (Exception e) {
            log.error("[createProduct] error al enviar el command de creación");
        }

        return new ResponseEntity<>("lalala POST de " + createProductRestModel.getTitle() + " con id " + createProductCommand.getProductId(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getProduct() {

        return ResponseEntity.ok("toma product! En puerto: " + env.getProperty("local.server.port"));
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
