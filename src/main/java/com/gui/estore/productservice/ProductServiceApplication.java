package com.gui.estore.productservice;

import com.gui.estore.productservice.commands.interceptors.CreateProductCommandInterceptor;
import com.gui.estore.productservice.exceptions.ProductServiceEventHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Autowired
    public void registerCreateProductCommandInterceptor(ApplicationContext context,
                                                        CommandBus commandBus) {
        // del contexto de la app cogemos el bean, que es nuestro interceptor y lo registramos en el commandBus
        commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
    }

    @Autowired
    public void configure(EventProcessingConfigurer config) {

        // registramos al ejecutar la app en el HANDLER "product-group" el ProductServiceEventHandler para las excepciones
        config.registerListenerInvocationErrorHandler("product-group",
                conf -> new ProductServiceEventHandler());

        // de AXON
//        config.registerListenerInvocationErrorHandler("product-group",
//                conf -> PropagatingErrorHandler.instance());
    }

}
