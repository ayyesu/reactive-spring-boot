package com.ayyesu.reactiveprogramming.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Document(collection = "customers")
public class Customer {

    @Id
    private String id;
    private String name;
    private String job;

    public  Customer(){}
    public Customer(String name, String job){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.job = job;
    }
}
