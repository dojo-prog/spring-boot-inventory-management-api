package com.doj0.inventory_management_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doj0.inventory_management_api.model.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, String> {
    List<StockMovement> findByProductId(String productId);
}
