package com.doj0.inventory_management_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doj0.inventory_management_api.model.ProductSupplier;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, String> {
    Optional<ProductSupplier> findBySupplierSku(String sku);
    Optional<ProductSupplier> findBySupplierSkuAndIdNot(String supplierSku, String id);
    Optional<ProductSupplier> findByProductIdAndSupplierId(String productId, String supplierId);
    List<ProductSupplier> findByProductId(String productId);
}
