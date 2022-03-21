package com.gui.productservice.commands.interceptors;

import com.gui.productservice.commands.CreateProductCommand;
import com.gui.productservice.exceptions.PriceLowerThanZeroException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

@Component
@Slf4j
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {

        // por este método pasan TODOS los COMMANDS antes de llegar al HANDLER
        return (index, command) -> {

            log.info("COMMAND interceptado: " + command.getPayloadType());

            // si es un createProductCommand validamos
            if (command.getPayloadType().equals(CreateProductCommand.class)) {

                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();

                isPriceGreaterThanZero(createProductCommand.getPrice());
                isTitleBlank(createProductCommand.getTitle());
            }
            return  command;
        };
    }

    private void isPriceGreaterThanZero(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PriceLowerThanZeroException("Precio menor que cero");
        }
    }

    private void isTitleBlank(String title) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("Título vacío");
        }
    }
}
