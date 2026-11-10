package com.doj0.inventory_management_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.doj0.inventory_management_api.dto.request.CreateProductSupplierRequestDto;
import com.doj0.inventory_management_api.dto.request.UpdateProductSupplierRequestDto;
import com.doj0.inventory_management_api.exception.ResourceNotFoundException;
import com.doj0.inventory_management_api.model.Product;
import com.doj0.inventory_management_api.model.ProductSupplier;
import com.doj0.inventory_management_api.model.Supplier;
import com.doj0.inventory_management_api.repository.ProductRepository;
import com.doj0.inventory_management_api.repository.ProductSupplierRepository;
import com.doj0.inventory_management_api.repository.SupplierRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ProductSupplierService {

    private final ProductSupplierRepository productSupplierRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public Page<ProductSupplier> getSuppliersByProductId(Pageable pageable, String productId) {
        return productSupplierRepository.findByProductId(productId, pageable);
    }

    @Transactional 
    public ProductSupplier assignSupplierToProduct(CreateProductSupplierRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Supplier supplier = supplierRepository.findById(requestDto.getSupplierId())
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        boolean existing = productSupplierRepository.findByProductIdAndSupplierId(
            requestDto.getProductId(), 
            requestDto.getSupplierId()
        ).isPresent();

        if (existing) {
            throw new IllegalArgumentException("This product is already registerd with this supplier");
        }

        if (requestDto.getSupplierSku() != null) {
            boolean skuExists = productSupplierRepository.findBySupplierSku(requestDto.getSupplierSku()).isPresent();

            if (skuExists) {
                throw new IllegalArgumentException("Supplier SKU already exists");
            }
        }

        ProductSupplier productSupplier = ProductSupplier.builder()
            .product(product)
            .supplier(supplier)
            .supplierSku(requestDto.getSupplierSku())
            .unitCost(requestDto.getUnitCost())
            .build();

        return productSupplierRepository.save(productSupplier);
    }

    @Transactional
    public ProductSupplier updateProductSupplier(String productSupplierId, UpdateProductSupplierRequestDto requestDto) {
        ProductSupplier productSupplier = productSupplierRepository.findById(productSupplierId)
            .orElseThrow(() -> new ResourceNotFoundException("Product-Supplier relation not found"));

        if (
            requestDto.getSupplierSku() != null && 
            !requestDto.getSupplierSku().equals(productSupplier.getSupplierSku())
        ) {
            boolean skuExists = productSupplierRepository.findBySupplierSkuAndIdNot(requestDto.getSupplierSku(), productSupplierId).isPresent();
            if (skuExists) {
                throw new IllegalArgumentException("Supplier SKU already exists");
            }
            productSupplier.setSupplierSku(requestDto.getSupplierSku());
        }

        if (requestDto.getUnitCost() != null) {
            productSupplier.setUnitCost(requestDto.getUnitCost().orElse(null));
        }

        return productSupplierRepository.save(productSupplier);
    }

    @Transactional
    public void removeSupplierFromProduct(String productSupplierId) {
        ProductSupplier productSupplier = productSupplierRepository.findById(productSupplierId)
            .orElseThrow(() -> new IllegalArgumentException("Product-Supplier relation not found"));

        productSupplierRepository.delete(productSupplier);
    }
}

