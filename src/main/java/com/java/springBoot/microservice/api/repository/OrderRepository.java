package com.java.springBoot.microservice.api.repository;

import com.java.springBoot.microservice.api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
