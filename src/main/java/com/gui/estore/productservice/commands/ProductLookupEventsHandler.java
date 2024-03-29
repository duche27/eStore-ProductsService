package com.gui.estore.productservice.commands;

import com.gui.estore.productservice.core.events.ProductCreatedEvent;
import com.gui.estore.productservice.core.events.data.ProductLookupEntity;
import com.gui.estore.productservice.core.events.data.ProductLookupRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
// agrupado con ProductEventsHandler para compartir hilo de ejecución (por rollbacks)
public class ProductLookupEventsHandler {

    ProductLookupRepository productLookupRepository;

    public ProductLookupEventsHandler(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {

        // la comprobación de existencia se hace en el CreateProductCommandInterceptor
        ProductLookupEntity productLookupEntity = new ProductLookupEntity(event.getProductId(), event.getTitle());

        productLookupRepository.save(productLookupEntity);
    }

    @ResetHandler
    public void reset() {
        productLookupRepository.deleteAll();
    }
}
