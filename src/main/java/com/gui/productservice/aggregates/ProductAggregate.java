package com.gui.productservice.aggregates;

import com.gui.productservice.commands.CreateProductCommand;
import com.gui.productservice.core.events.ProductCreatedEvent;
import com.gui.productservice.exceptions.BlankTitleException;
import com.gui.productservice.exceptions.PriceLowerThanZeroException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate() { }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) throws Exception {

        // validaciones
        isPriceGreaterThanZero(createProductCommand.getPrice());
        isTitleBlank(createProductCommand.getTitle());

        // creamos evento una vez pasadas las validaciones
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

        // publicamos evento y lo mandamos al eventHandler
        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.title = productCreatedEvent.getTitle();
        this.quantity = productCreatedEvent.getQuantity();
    }

    private void isPriceGreaterThanZero(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PriceLowerThanZeroException("Precio menor que cero");
        }
    }

    private void isTitleBlank(String title) {
        if (title.isBlank()) {
            throw new BlankTitleException("Título vacío");
        }
    }
}
