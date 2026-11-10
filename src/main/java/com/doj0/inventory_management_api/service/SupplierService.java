package com.doj0.inventory_management_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.doj0.inventory_management_api.dto.request.CreateSupplierRequestDto;
import com.doj0.inventory_management_api.dto.request.UpdateSupplierRequestDto;
import com.doj0.inventory_management_api.exception.ResourceNotFoundException;
import com.doj0.inventory_management_api.model.Supplier;
import com.doj0.inventory_management_api.repository.SupplierRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public Page<Supplier> getSuppliers(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }

    public Supplier getSupplierById(String supplierId) {
        return supplierRepository.findById(supplierId)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
    }

    @Transactional 
    public Supplier createSupplier(CreateSupplierRequestDto requestDto) {
        boolean existing = supplierRepository.findByName(requestDto.getName()).isPresent();

        if (existing) {
            throw new IllegalArgumentException("This supplier name is already registered");
        }

        Supplier supplier = Supplier.builder()
            .name(requestDto.getName())
            .contactPerson(requestDto.getContactPerson())
            .email(requestDto.getEmail())
            .phone(requestDto.getPhone())
            .address(requestDto.getAddress())
            .build();

        return supplierRepository.save(supplier);        
    }

    @Transactional 
    public Supplier updatSupplier(String supplierId,  UpdateSupplierRequestDto requestDto) {
        Supplier supplier = supplierRepository.findById(supplierId)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
        
        if (requestDto.getName() != null && !requestDto.getName().equals(supplier.getName())) {
            boolean existing = supplierRepository.findByNameAndIdNot(
                requestDto.getName(), 
                supplier.getId()
            ).isPresent();

            if (existing) {
                throw new IllegalArgumentException("This supplier name is already registered");
            }
            supplier.setName(requestDto.getName());
        }

        if (requestDto.getContactPerson() != null) {
            supplier.setContactPerson(requestDto.getContactPerson().orElse(null));
        }
        
        if (requestDto.getEmail() != null) {
            supplier.setEmail(requestDto.getEmail().orElse(null));
        }
        
        if (requestDto.getPhone() != null) {
            supplier.setPhone(requestDto.getPhone().orElse(null));
        }
        
        if (requestDto.getAddress() != null) {
            supplier.setAddress(requestDto.getAddress().orElse(null));
        }
        
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(String supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
    
        supplierRepository.delete(supplier);
    }
}
