package com.gui.estore.productservice.commands.rest;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/management")
public class EventsReplayController {

    // para tener acceso al eventProcessor que necesitamos para re-producir eventos
    @Autowired
    EventProcessingConfiguration eventProcessingConfiguration;

    @PostMapping("/eventProcessor/{processorName}/reset")
    public ResponseEntity<String> replayEvents(@PathVariable String processorName) {

        // buscamos el TrackingEventProcessor que queremos reproducir
        Optional<TrackingEventProcessor> trackingEventProcessor =
                eventProcessingConfiguration.eventProcessor(processorName, TrackingEventProcessor.class);

        if (trackingEventProcessor.isPresent()) {
            TrackingEventProcessor eventProcessor = trackingEventProcessor.get();
            eventProcessor.shutDown();      // apagamos el eventProcessor
            eventProcessor.resetTokens();   // reseteamos sus tokens
            eventProcessor.start();         // iniciamos la replicaciones de eventos

            return ResponseEntity.ok()
                    .body(String.format("Event processor [%s] has been reset", processorName));
        } else {
            return ResponseEntity.badRequest()
                    .body(String.format("Event processor with name [%s] is not a tracking event processor has been reset", processorName));
        }
    }
}
