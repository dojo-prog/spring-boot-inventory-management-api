package com.doj0.inventory_management_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doj0.inventory_management_api.exception.ResourceNotFoundException;
import com.doj0.inventory_management_api.model.StockMovement;
import com.doj0.inventory_management_api.repository.StockMovementRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;

    public List<StockMovement> getProductStockMovements(String productId) {
        return stockMovementRepository.findByProductId(productId);
    }

    public StockMovement getStockMovementById(String stockMovementId) {
        return stockMovementRepository.findById(stockMovementId)
            .orElseThrow(() -> new ResourceNotFoundException(stockMovementId));
    }
}
