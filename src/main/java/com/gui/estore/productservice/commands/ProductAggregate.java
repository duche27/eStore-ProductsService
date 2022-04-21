package com.gui.estore.productservice.commands;

import com.gui.estore.core.commands.CancelProductReservationCommand;
import com.gui.estore.core.commands.ReserveProductCommand;
import com.gui.estore.core.events.ProductReservationCancelledEvent;
import com.gui.estore.core.events.ProductReservedEvent;
import com.gui.estore.productservice.exceptions.BlankTitleException;
import com.gui.estore.productservice.exceptions.OutOfStockException;
import com.gui.estore.productservice.exceptions.PriceLowerThanZeroException;
import com.gui.estore.productservice.core.events.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Slf4j
@Aggregate(snapshotTriggerDefinition = "productSnapshotTriggerDefinition")
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private int quantity;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {

        // VALIDACIONES
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

    @CommandHandler
    public void handle(ReserveProductCommand reserveProductCommand) {

        // VALIDACIONES
        // no necesitamos hacer query a la BD de entidades para saber el stock
        // xq AXON recupera el estado cuando el AGGREGATE se carga (replica todos los eventos anteriores)
        if (quantity < reserveProductCommand.getQuantity())
            throw new OutOfStockException("No tenemos stock suficiente del producto " + reserveProductCommand.getProductId());

        // creamos evento una vez pasadas las validaciones
        ProductReservedEvent productReservedEvent = ProductReservedEvent.builder()
                .orderId(reserveProductCommand.getOrderId())
                .productId(reserveProductCommand.getProductId())
                .quantity(reserveProductCommand.getQuantity())
                .userId(reserveProductCommand.getUserId())
                .build();

        // publicamos evento y lo mandamos al eventHandler
        AggregateLifecycle.apply(productReservedEvent);
    }

    @EventSourcingHandler
    public void on(ProductReservedEvent productReservedEvent) {
        this.quantity -= productReservedEvent.getQuantity();
    }

    @CommandHandler
    public void handle(CancelProductReservationCommand cancelProductReservationCommand) {

        ProductReservationCancelledEvent productReservationCancelledEvent = ProductReservationCancelledEvent.builder()
                .orderId(cancelProductReservationCommand.getOrderId())
                .productId(cancelProductReservationCommand.getProductId())
                .quantity(cancelProductReservationCommand.getQuantity())
                .userId(cancelProductReservationCommand.getUserId())
                .reason(cancelProductReservationCommand.getReason())
                .build();

        AggregateLifecycle.apply(productReservationCancelledEvent);
    }

    @EventSourcingHandler
    public void on(ProductReservationCancelledEvent productReservationCancelledEvent) {

        this.quantity += productReservationCancelledEvent.getQuantity();
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
