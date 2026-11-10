package com.doj0.inventory_management_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doj0.inventory_management_api.dto.request.CreateProductRequestDto;
import com.doj0.inventory_management_api.dto.request.UpdateProductRequestDto;
import com.doj0.inventory_management_api.dto.request.UpdateProductStockRequestDto;
import com.doj0.inventory_management_api.enums.StockMovementType;
import com.doj0.inventory_management_api.exception.ResourceNotFoundException;
import com.doj0.inventory_management_api.model.Category;
import com.doj0.inventory_management_api.model.Product;
import com.doj0.inventory_management_api.model.StockMovement;
import com.doj0.inventory_management_api.model.User;
import com.doj0.inventory_management_api.repository.CategoryRepository;
import com.doj0.inventory_management_api.repository.ProductRepository;
import com.doj0.inventory_management_api.repository.StockMovementRepository;
import com.doj0.inventory_management_api.util.ProductSkuUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StockMovementRepository stockMovementRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Transactional 
    public Product createProduct(CreateProductRequestDto requestDto) {
        Category category = categoryRepository.findById(requestDto.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        boolean existing = productRepository.findByName(requestDto.getName()).isPresent();

        if (existing) {
            throw new IllegalArgumentException("A product with this name already exists");
        }

        String baseSku = ProductSkuUtils.generateSku(
            category.getName(), 
            requestDto.getName()
        );
        String uniqueSku = baseSku;

        while (productRepository.findBySku(uniqueSku).isPresent()) {
            uniqueSku = ProductSkuUtils.generateSku(
                category.getName(), 
                requestDto.getName()
            );
        }

        Product product = Product.builder()
            .category(category)
            .name(requestDto.getName())
            .sku(uniqueSku)
            .description(requestDto.getDescription())
            .unit(requestDto.getUnit())
            .unitCost(requestDto.getUnitCost())
            .stockQuantity(requestDto.getStockQuantity())
            .reorderLevel(requestDto.getReorderLevel())
            .status(requestDto.getStatus())
            .build();

        productRepository.save(product);

        category.addProduct(product);
        product.addCategory(category);

        return product;
    }

    @Transactional 
    public Product updateProduct(String productId, UpdateProductRequestDto requestDto) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (
            requestDto.getCategoryId() != null && 
            (product.getCategory() == null || !product.getCategory().getId().equals(requestDto.getCategoryId())) 
        ) {
            Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

            product.setCategory(category);
            category.addProduct(product); 
        }

        if (requestDto.getName() != null && !product.getName().equals(requestDto.getName())) {
            boolean existing = productRepository.findByNameAndIdNot(
                requestDto.getName(), 
                productId
            ).isPresent();

            if (existing) {
                throw new IllegalArgumentException("A product with this name already exists");
            }

            product.setName(requestDto.getName());
        }

        if (requestDto.getDescription() != null) {
            product.setDescription(requestDto.getDescription());
        }

        if (requestDto.getUnit() != null) {
            product.setUnit(requestDto.getUnit());
        }

        if (requestDto.getUnitCost() != null) {
            product.setUnitCost(requestDto.getUnitCost());
        }

        if (requestDto.getStockQuantity() != null) {
            product.setStockQuantity(requestDto.getStockQuantity());
        }

        if (requestDto.getReorderLevel() != null) {
            product.setReorderLevel(requestDto.getReorderLevel());
        }

        if (requestDto.getStatus() != null) {
            product.setStatus(requestDto.getStatus());
        }

        return productRepository.save(product);
    }

    @Transactional 
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Category category = product.getCategory();
        category.removeProduct(product);

        productRepository.delete(product);
    }

    @Transactional 
    public Product updateProductStock(
        User currentUser,
        String productId, 
        UpdateProductStockRequestDto requestDto
    ) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        StockMovementType type = requestDto.getType();
        int currentStock = product.getStockQuantity();
        int requestedQty = requestDto.getQuantity();
        int newQuantity;

        if (type == StockMovementType.STOCK_IN) {
            newQuantity = currentStock + requestedQty;
        } 
        else if (type == StockMovementType.STOCK_OUT) {
            newQuantity = currentStock - requestedQty; 

            if (newQuantity < 0) {
                throw new IllegalArgumentException("Insufficient stock quantity");
            }
        } 
        else {
            throw new IllegalArgumentException("Invalid stock movement operation type");
        }

        product.setStockQuantity(newQuantity);

        StockMovement stockMovement = StockMovement.builder()
            .product(product)
            .user(currentUser)
            .type(type)
            .quantity(requestedQty)
            .previousQuantity(currentStock)
            .newQuantity(newQuantity)
            .reason(requestDto.getReason())
            .reference(requestDto.getReference())
            .build();

        stockMovementRepository.save(stockMovement);
        
        return productRepository.save(product); 
    }
}
