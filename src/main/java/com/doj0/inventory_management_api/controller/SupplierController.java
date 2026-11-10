package com.doj0.inventory_management_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doj0.inventory_management_api.dto.request.CreateSupplierRequestDto;
import com.doj0.inventory_management_api.dto.request.UpdateSupplierRequestDto;
import com.doj0.inventory_management_api.dto.response.ApiResponse;
import com.doj0.inventory_management_api.model.Supplier;
import com.doj0.inventory_management_api.service.SupplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor 
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping 
    public ResponseEntity<?> getSuppliers(Pageable pageable) {
        Page<Supplier> suppliers = supplierService.getSuppliers(pageable);

        return ResponseEntity.ok(
            ApiResponse.success(
                suppliers
            )
        );
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<?> getSupplierById(
        @PathVariable String supplierId
    ) {
        Supplier supplier = supplierService.getSupplierById(supplierId);

        return ResponseEntity.ok(
            ApiResponse.success(
                supplier
            )
        );
    }

    @PostMapping
    public ResponseEntity<?> createSupplier(
        @Valid @RequestBody  CreateSupplierRequestDto requestDto
    ) {
        Supplier supplier = supplierService.createSupplier(requestDto);

        return  ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.success(
                "Supplier successfully added to the records",
                supplier
            )
        );
    }

    @PatchMapping("/{supplierId}")
    public ResponseEntity<?> updateSupplier(
        @PathVariable String supplierId,
        @Valid @RequestBody UpdateSupplierRequestDto requestDto
    ) {
        Supplier supplier = supplierService.updatSupplier(supplierId, requestDto);

        return ResponseEntity.ok(
            ApiResponse.success(
                "Supplier details successfully updated",
                supplier
            )
        );
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<?> deleteSupplier(
        @PathVariable String supplierId
    ) {
        supplierService.deleteSupplier(supplierId);

        return ResponseEntity.ok(
            ApiResponse.message(
                "Supplier has been successfully deleted from the records"
            )
        );
    }
}
