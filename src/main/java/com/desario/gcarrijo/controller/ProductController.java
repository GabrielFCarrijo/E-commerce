package com.desario.gcarrijo.controller;

import com.desario.gcarrijo.entity.dto.ProductRequestDTO;
import com.desario.gcarrijo.entity.dto.ProductResponseDTO;
import com.desario.gcarrijo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.create(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{uuid}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable("uuid") UUID uuid,@RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.update(uuid, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable("uuid") UUID uuid) {
        productService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(productService.getById(uuid));
    }
} 