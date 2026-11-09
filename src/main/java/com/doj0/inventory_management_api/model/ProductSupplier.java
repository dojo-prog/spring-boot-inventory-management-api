package com.doj0.inventory_management_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(
    name = "product_suppliers",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id", "supplier_id"})
    }
)
@Data 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
@EntityListeners(AuditingEntityListener.class)
public class ProductSupplier {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = "supplier_sku")
    private String supplierSku;

    @Column(name = "unit_cost", precision = 10, scale = 2)
    private BigDecimal unitCost;

    @CreatedDate 
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; 

    @LastModifiedDate 
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; 
}
