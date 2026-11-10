package com.doj0.inventory_management_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doj0.inventory_management_api.dto.response.ApiResponse;
import com.doj0.inventory_management_api.model.StockMovement;
import com.doj0.inventory_management_api.service.StockMovementService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/v1")
@RequiredArgsConstructor 
public class StockMovementController {

    private final StockMovementService stockMovementService;


    @GetMapping("/products/{productId}/stock-movements") 
    public ResponseEntity<?> getProductStockMovements(
        @PathVariable String productId
    ) {
        List<StockMovement> productStockMovements =stockMovementService.getProductStockMovements(productId);

        return ResponseEntity.ok(
            ApiResponse.success(
                productStockMovements
            )
        );
    }

    @GetMapping("/stock-movements/{stockMovementId}") 
    public ResponseEntity<?> getStockMovementById(
        @PathVariable String stockMovementId
    ) {
        StockMovement stockMovement = stockMovementService.getStockMovementById(stockMovementId);

        return ResponseEntity.ok(
            ApiResponse.success(
                stockMovement
            )
        );
    }

}
