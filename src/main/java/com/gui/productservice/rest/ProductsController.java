package com.gui.productservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private Environment env;
    
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRestModel createProductRestModel) {

        return new ResponseEntity<>("lalala POST de " + createProductRestModel.getTitle(), HttpStatus.CREATED);
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
