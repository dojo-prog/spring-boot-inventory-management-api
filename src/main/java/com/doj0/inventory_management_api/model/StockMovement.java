package com.doj0.inventory_management_api.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.doj0.inventory_management_api.enums.StockMovementType;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "stock_movements")
@Data 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
@EntityListeners(AuditingEntityListener.class)
public class StockMovement {

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockMovementType type;
    
    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "previous_quantity", nullable = false)
    private Integer previousQuantity;

    @Column(name = "new_quantity", nullable = false)
    private Integer newQuantity;

    @Column(nullable = false, length = 250)
    private String reason;
    
    @Column(nullable = true)
    private String reference;

    @CreatedDate 
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
