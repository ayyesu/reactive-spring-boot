package com.ayyesu.reactiveprogramming.controller;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class SSEController {

    @GetMapping(value = "/pdf-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> produceEvents() throws IOException{
        try {
            File file = new File("src/main/resources/sample.pdf");
            PDDocument document = Loader.loadPDF(file);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();

            List<String> lines = Arrays.asList(text.split("\\r?\\n"));
            return Flux.fromIterable(lines)
                    .delayElements(Duration.ofSeconds(1))
                    .map(line -> ServerSentEvent.builder(line).build());
        }catch (Exception e){
            return Flux.just(ServerSentEvent.builder("Error reading pdf: " + e.getMessage()).build());
        }
    }

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
