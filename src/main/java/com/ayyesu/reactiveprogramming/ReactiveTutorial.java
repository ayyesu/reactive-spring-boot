package com.ayyesu.reactiveprogramming;


import reactor.core.publisher.Mono;

public class ReactiveTutorial {

    // Mono is just for a single data
    private Mono<String> testMono(){
        return Mono.just("Java"); // For null values we use Mono.justorEmpty()
    }

    public static void main(String[] args) {
        ReactiveTutorial reactiveTutorial = new ReactiveTutorial();
        reactiveTutorial.testMono()
                .subscribe(data -> System.out.println(data));
    }
}