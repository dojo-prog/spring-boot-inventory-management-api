package com.doj0.inventory_management_api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "categories")
@Builder 
@NoArgsConstructor 
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class) 
public class Category {

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true, length = 50)
    private String name; 

    @Column(nullable = true, length = 360)
    private String description;

    @CreatedDate 
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; 

    @LastModifiedDate 
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Relationships
    @Builder.Default
    @JsonIgnore 
    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST, orphanRemoval = false)
    private List<Product> products = new ArrayList<>();
    
    // Relationship Helpers
    public void addProduct(Product product) {
        products.add(product);
        product.setCategory(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.setCategory(null);
    }
}
