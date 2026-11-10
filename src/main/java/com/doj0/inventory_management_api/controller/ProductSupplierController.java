package com.doj0.inventory_management_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doj0.inventory_management_api.dto.request.CreateProductSupplierRequestDto;
import com.doj0.inventory_management_api.dto.request.UpdateProductSupplierRequestDto;
import com.doj0.inventory_management_api.dto.response.ApiResponse;
import com.doj0.inventory_management_api.model.ProductSupplier;
import com.doj0.inventory_management_api.service.ProductSupplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/v1/product-suppliers")
@RequiredArgsConstructor 
public class ProductSupplierController {

    private final ProductSupplierService productSupplierService;

    @GetMapping("products/{productId}")
    public ResponseEntity<?> getSuppliersByProductId(
        @PathVariable String productId
    ) {
        List<ProductSupplier> productSuppliers = productSupplierService.getSuppliersByProductId(productId);

        return ResponseEntity.ok(
            ApiResponse.success(productSuppliers)
        );
    }

    @PostMapping 
    public ResponseEntity<?> assignSupplierToProduct(
        @Valid @RequestBody CreateProductSupplierRequestDto requestDto
    ) {
        ProductSupplier productSupplier = productSupplierService.assignSupplierToProduct(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.success(
                "Product registered to the supplier",
                productSupplier
            )
        );
    }

    @PatchMapping("/{productSupplierId}")
    public ResponseEntity<?> updateProductSupplier(
        @PathVariable String productSupplierId,
        @Valid @RequestBody UpdateProductSupplierRequestDto requestDto
    ) {
        ProductSupplier productSupplier = productSupplierService.updateProductSupplier(productSupplierId, requestDto);

        return ResponseEntity.ok(
            ApiResponse.success(
                "Product supplier details has been successfully updated",
                productSupplier
            )
        );
    } 

    @DeleteMapping("/{productSupplierId}")
    public ResponseEntity<?> removeSupplierFromProduct(
        @PathVariable String productSupplierId
    ) {
        productSupplierService.removeSupplierFromProduct(productSupplierId);

        return ResponseEntity.ok(
            ApiResponse.message("Product supplier relation has been removed")
        );
    }

}
