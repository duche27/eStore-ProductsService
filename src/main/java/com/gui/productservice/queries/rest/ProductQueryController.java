package com.gui.productservice.queries.rest;

import com.gui.productservice.queries.FindProductsQuery;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

    private final QueryGateway queryGateway;
    private final Environment env;

    public ProductQueryController(QueryGateway queryGateway, Environment env) {
        this.queryGateway = queryGateway;
        this.env = env;
    }

    @GetMapping("getProduct")
    public ResponseEntity<?> getProduct() {

        return ResponseEntity.ok("toma product! En puerto: " + env.getProperty("local.server.port"));
    }

    @GetMapping("getProducts")
    public List<ProductRestModel> getProducts() {

        FindProductsQuery findProductsQuery = new FindProductsQuery();

        // join() porque devuelve un CompletableFuture
        List<ProductRestModel> products = queryGateway.query(findProductsQuery, ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();

        return products;
    }
}
