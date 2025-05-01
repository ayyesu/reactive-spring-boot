package com.ayyesu.reactiveprogramming;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class ReactiveTutorial {

    // Mono is just for a single data
    private Mono<String> testMono(){
        return Mono.just("Java"); // For null values we use Mono.justorEmpty()
    }

    // Flux accepts one or more data
    private Flux<String> testFlux(){
        List<String> tutorial = List.of("Java", "Tutorial", "Reactive", "Programming");
        return Flux.fromIterable(tutorial);
        // return Flux.just("Java", "Tutorial", "Reactive", "Programming");
    }

    public static void main(String[] args) {
        ReactiveTutorial reactiveTutorial = new ReactiveTutorial();
//        reactiveTutorial.testMono()
//                .subscribe(data -> System.out.println(data));

        reactiveTutorial.testFlux()
                .subscribe(System.out::println);
    }
}