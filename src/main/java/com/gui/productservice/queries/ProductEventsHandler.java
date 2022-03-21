package com.gui.productservice.queries;

import com.gui.productservice.core.events.ProductCreatedEvent;
import com.gui.productservice.model.ProductEntity;
import com.gui.productservice.repositories.ProductRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductEventsHandler {

    private final ProductRepository productRepository;

    @Autowired
    public ProductEventsHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {

        ProductEntity productEntity = new ProductEntity();

        BeanUtils.copyProperties(productCreatedEvent, productEntity);

        productRepository.save(productEntity);
    }
}
