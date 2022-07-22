package com.java.springBoot.microservice.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "ORDER_TB")
public class Order {

    @Id
    private int id;
    private String name;
    private double price;
    private int qty;

}
