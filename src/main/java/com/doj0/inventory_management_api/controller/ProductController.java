package com.doj0.inventory_management_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doj0.inventory_management_api.dto.request.CreateProductRequestDto;
import com.doj0.inventory_management_api.dto.request.UpdateProductRequestDto;
import com.doj0.inventory_management_api.dto.request.UpdateProductStockRequestDto;
import com.doj0.inventory_management_api.dto.response.ApiResponse;
import com.doj0.inventory_management_api.model.Product;
import com.doj0.inventory_management_api.model.User;
import com.doj0.inventory_management_api.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor 
public class ProductController {

    private final ProductService productService;

    @GetMapping 
    public ResponseEntity<?> getProducts() {
        List<Product> products = productService.getProducts();

        return ResponseEntity.ok(
            ApiResponse.success(
                products
            )
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(
        @PathVariable String productId
    ) {
        Product product = productService.getProductById(productId);

        return ResponseEntity.ok(
            ApiResponse.success(
                product
            )
        );
    }

    @PostMapping 
    public ResponseEntity<?> createProduct(
        @Valid @RequestBody CreateProductRequestDto requestDto
    ) {
        Product product = productService.createProduct(requestDto);

        return ResponseEntity.ok(
            ApiResponse.success(
                "Product successfully created",
                product
            )
        );
    }

    @PatchMapping ("/{productId}")
    public ResponseEntity<?> updateProduct(
        @PathVariable String productId,
        @Valid @RequestBody UpdateProductRequestDto requestDto
    ) {
        Product product = productService.updateProduct(productId, requestDto);

        return ResponseEntity.ok(
            ApiResponse.success(
                "Product details successfully updated",
                product
            )
        );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(
        @PathVariable String productId
    ) {
        productService.deleteProduct(productId);

        return ResponseEntity.ok(
            ApiResponse.message("Product successfully removed from the records")
        );
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<?> updateProductStock(
        @AuthenticationPrincipal User user, 
        @PathVariable String productId, 
        @Valid @RequestBody UpdateProductStockRequestDto requestDto
    ) {
        Product product = productService.updateProductStock(user, productId, requestDto);

        return ResponseEntity.ok(
            ApiResponse.success(
                "Product's stock quantity has been successfully updated",
                product
            )
        );
    }
}
