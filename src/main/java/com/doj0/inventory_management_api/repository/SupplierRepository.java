package com.doj0.inventory_management_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doj0.inventory_management_api.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, String> {
    Optional<Supplier> findByName(String name);
    Optional<Supplier> findByNameAndIdNot(String name, String id);   
}
