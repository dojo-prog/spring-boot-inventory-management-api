package com.doj0.inventory_management_api.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "suppliers")
@Data 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
@EntityListeners(AuditingEntityListener.class) 
public class Supplier {

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 200)
    private String name;  

    @Column(nullable = true, length = 150)
    private String contact_person;
    
    @Column(nullable = true, length = 150)
    private String email;  

    @Column(nullable = true, length = 11)
    private String phone;

    @Column(nullable = true, length = 400)
    private String address;

    @CreatedDate 
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate 
    @Column(name = "updated_at", nullable = false) 
    private LocalDateTime updatedAt;

    // Relationships
    @Builder.Default
    @JsonIgnore 
    @ManyToMany(mappedBy = "suppliers") 
    private Set<Product> products = new HashSet<>();
    
    // Relationsip Helpers
    public void addProduct(Product product) {
        products.add(product);
        product.getSuppliers().add(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.getSuppliers().remove(this);
    }
}
