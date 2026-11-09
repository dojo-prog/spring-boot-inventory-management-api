package com.doj0.inventory_management_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.doj0.inventory_management_api.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "products")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@EntityListeners(AuditingEntityListener.class) 
public class Product {

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; 
    
    @Column(nullable = false, unique = true, length = 150)
    private String name; 
    
    @Column(nullable = false, unique = true)
    private String sku; 
    
    @Column(nullable = false, length = 700) 
    private String description; 
    
    @Column(nullable = false, length = 50) 
    private String unit; 
    
    @Column(name = "unit_cost", nullable = false, precision = 10, scale = 2 ) 
    private BigDecimal unitCost;  
    
    @Column(nullable = false)
    private Integer stockQuantity;  
    
    @Column(nullable = false)
    private Integer reorderLevel;  

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @CreatedDate 
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate 
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Relationships 
    @JsonIgnore 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @Builder.Default
    @JsonIgnore 
    @ManyToMany 
    @JoinTable (
        name = "product_suppliers",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private Set<Supplier> suppliers = new HashSet<>();

    // Relationship helpers
    public void addCategory(Category category) {
        this.category = category;
    }

    public void removeCategory() {
        this.category = null;
    }

    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
        supplier.getProducts().add(this);
    }

    public void removeSupplier(Supplier supplier) {
        suppliers.remove(supplier);
        supplier.getProducts().remove(this);
    }
}
