package com.cloudcart.productservice.repositories;


import com.cloudcart.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    List<Product> findByCategoryId(String categoryId);
    List<Product> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(String ProductName, String description, String categoryName);

}
