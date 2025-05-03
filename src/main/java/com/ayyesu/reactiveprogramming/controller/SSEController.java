package com.ayyesu.reactiveprogramming.controller;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
public class SSEController {

    @GetMapping("/events")
    public Flux<ServerSentEvent<String>> getEvents(){
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("message")
                        .data("Event # " + sequence + " at " + LocalDateTime.now())
                        .build());
    }
}
