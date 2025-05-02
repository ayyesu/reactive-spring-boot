package com.ayyesu.reactiveprogramming.extra;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

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

    private Flux<String> testMap(){
        Flux<String> flux = Flux.just("Java", "Tutorial", "Reactive", "Programming");
        return flux.map(data -> data.toUpperCase(Locale.ROOT));
    }

    private Flux<String> testFlatMap(){
        Flux<String> flux = Flux.just("Java", "Tutorial", "Reactive", "Programming");
        return flux.flatMap(data -> Mono.just(data.toUpperCase(Locale.ROOT)));
    }

    private Flux<String> testSkip(){
        Flux<String> flux = Flux.just("Java", "Tutorial", "Reactive", "Programming");
//        return flux.skip(2);
        return flux.delayElements(Duration.ofSeconds(1));
    }

    private  Flux<Integer> testComplexSkip(){
        Flux<Integer> flux = Flux.range(1, 20);
        return flux;
    }

    public static void main(String[] args) {
        ReactiveTutorial reactiveTutorial = new ReactiveTutorial();
//        reactiveTutorial.testMono()
//                .subscribe(data -> System.out.println(data));

        reactiveTutorial.testComplexSkip()
                .subscribe(System.out::println);
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}