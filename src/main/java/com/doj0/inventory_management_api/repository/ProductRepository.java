package com.doj0.inventory_management_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doj0.inventory_management_api.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByName(String name);
    Optional<Product> findBySku(String sku);
    Optional<Product> findByNameAndIdNot(String name, String id);
}
