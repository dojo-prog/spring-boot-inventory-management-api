package com.doj0.inventory_management_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doj0.inventory_management_api.model.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, String> {
    Page<StockMovement> findByProductId(String productId, Pageable pageable);
}
