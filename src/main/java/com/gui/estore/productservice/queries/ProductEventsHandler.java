package com.gui.estore.productservice.queries;

import com.gui.estore.productservice.model.ProductEntity;
import com.gui.estore.productservice.core.events.ProductCreatedEvent;
import com.gui.estore.productservice.repositories.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")   // agrupado con ProductLookupEventsHandler para compartir hilo de ejecución (por rollbacks)
public class ProductEventsHandler {

    private final ProductRepository productRepository;

    @Autowired
    public ProductEventsHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // lanza la excepción controlada si no persiste productEntity
    // sin persistir nada, es transaccional
    // de aquí va a ProductServiceEventHandler - después a ProductErrorHandler - excepción controlada
    @ExceptionHandler(resultType = Exception.class)
    private void handle(Exception exception) throws Exception {
        throw exception;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    private void handle(IllegalArgumentException exception) throws IllegalArgumentException {
//        throw IllegalArgumentException;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {

        ProductEntity productEntity = new ProductEntity();

        BeanUtils.copyProperties(productCreatedEvent, productEntity);

        try {
            productRepository.save(productEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
