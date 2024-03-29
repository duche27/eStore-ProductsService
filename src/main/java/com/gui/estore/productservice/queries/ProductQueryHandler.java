package com.gui.estore.productservice.queries;

import com.gui.estore.productservice.model.ProductEntity;
import com.gui.estore.productservice.queries.rest.ProductRestModel;
import com.gui.estore.productservice.repositories.ProductRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductQueryHandler {

    ProductRepository productRepository;

    public ProductQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public List<ProductRestModel> findProducts(FindProductsQuery findProductsQuery) {

        List<ProductEntity> storedProducts = productRepository.findAll();

        return storedProducts.stream()
                .map(productEntity -> {
                    ProductRestModel productRestModel = new ProductRestModel();
                    BeanUtils.copyProperties(productEntity, productRestModel);
                    return productRestModel;
                }).collect(Collectors.toList());
    }
}
