package com.doj0.inventory_management_api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.doj0.inventory_management_api.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "users")
@Data 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
@EntityListeners(AuditingEntityListener.class) 
public class User implements UserDetails {

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; 

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreatedDate 
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate 
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles;

    
    // Relationships 
    @Builder.Default
    @JsonIgnore 
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false)
    private List<StockMovement> stockMovements = new ArrayList<>(); 


    // Relationship Helper Methods
    public void addStockMovement(StockMovement sm) {
        stockMovements.add(sm);
        sm.setUser(this);
    }

    // User Details Methods Implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.name()))
            .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }
}
