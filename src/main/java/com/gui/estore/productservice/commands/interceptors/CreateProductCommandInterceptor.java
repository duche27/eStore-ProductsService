package com.gui.estore.productservice.commands.interceptors;

import com.gui.estore.productservice.core.events.data.ProductLookupRepository;
import com.gui.estore.productservice.commands.CreateProductCommand;
import com.gui.estore.productservice.exceptions.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Component
@Slf4j
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    ProductLookupRepository productLookupRepository;

    public CreateProductCommandInterceptor(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {

        // por este método pasan TODOS los COMMANDS antes de llegar al HANDLER
        return (index, command) -> {

            log.info("COMMAND interceptado: " + command.getPayloadType());

            // si es un createProductCommand pueden hacerse aquí las VALIDACIONES
            if (command.getPayloadType().equals(CreateProductCommand.class)) {

                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();

                if (productLookupRepository.findByProductIdOrTitle(createProductCommand.getProductId(), createProductCommand.getTitle())
                        .isPresent())
                    throw new ProductNotFoundException("Product with id " + createProductCommand.getProductId() +
                            " or title " + createProductCommand.getTitle() + " already exists");
            }

            return command;
        };
    }
}
