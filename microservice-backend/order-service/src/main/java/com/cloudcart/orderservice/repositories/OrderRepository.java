package com.cloudcart.orderservice.repositories;

import com.cloudcart.orderservice.modals.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {

    List<Order> findByUserIdOrderByPlacedOnDesc(String userId);


}
