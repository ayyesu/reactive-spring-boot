package com.ayyesu.reactiveprogramming.controller;

import com.ayyesu.reactiveprogramming.model.Customer;
import com.ayyesu.reactiveprogramming.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Map;

@RestController
public class DataControl {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @PostMapping("/customer/create")
    public Mono<Customer> createCustomer (@RequestBody Customer customer){
        return reactiveMongoTemplate.save(customer);
    }

    @GetMapping("/customer/find-by-id")
    public Mono<Customer> findCustomerById(@RequestParam("customerId") String customerId){
        return getCustomerById(customerId);
    }

    public Mono<Customer> getCustomerById(String customerId){
        Criteria criteria = Criteria.where("id").is(customerId);
        Query query = Query.query(criteria);
        return reactiveMongoTemplate.findOne(query, Customer.class);
    }

    @PostMapping("/order/create")
    public Mono<Order> createOrder (@RequestBody Order order){
        return reactiveMongoTemplate.save(order);
    }

    @GetMapping("/order/summary")
    public Mono<Map<String, Double>> getOrderSummary(){
       return reactiveMongoTemplate.findAll(Customer.class)
                .flatMap(customer -> Mono.zip(Mono.just(customer), calculateOrderSum(customer.getId())))
                .collectMap(Tuple2 -> Tuple2.getT1().getName(), Tuple2::getT2);
    }

    public Mono<Double> calculateOrderSum(String customerId){
        Criteria criteria = Criteria.where("customerId").is(customerId);
        return reactiveMongoTemplate.find(Query.query(criteria), Order.class)
                .map(Order::getTotal)
                .reduce(0d, Double::sum);

    }

}
