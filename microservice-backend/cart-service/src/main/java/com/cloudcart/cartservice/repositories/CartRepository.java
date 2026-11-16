package com.cloudcart.cartservice.repositories;


import com.cloudcart.cartservice.modals.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {
    Cart findByUserId(String userId);
    boolean existsByUserId(String userId);

}
