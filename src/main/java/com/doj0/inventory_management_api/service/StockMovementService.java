package com.doj0.inventory_management_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.doj0.inventory_management_api.exception.ResourceNotFoundException;
import com.doj0.inventory_management_api.model.StockMovement;
import com.doj0.inventory_management_api.repository.StockMovementRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;

    public Page<StockMovement> getProductStockMovements(String productId, Pageable pageable) {
        return stockMovementRepository.findByProductId(productId, pageable);
    }

    public StockMovement getStockMovementById(String stockMovementId) {
        return stockMovementRepository.findById(stockMovementId)
            .orElseThrow(() -> new ResourceNotFoundException(stockMovementId));
    }
}
