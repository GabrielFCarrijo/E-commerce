package com.desario.gcarrijo.service;

import com.desario.gcarrijo.config.exceptions.ProductNotFoundException;
import com.desario.gcarrijo.entity.Product;
import com.desario.gcarrijo.entity.dto.ProductRequestDTO;
import com.desario.gcarrijo.entity.dto.ProductResponseDTO;
import com.desario.gcarrijo.entity.mapper.ProductMapper;
import com.desario.gcarrijo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = ProductMapper.toEntity(dto);

        product = productRepository.save(product);
        return ProductMapper.toDTO(product);
    }

    public ProductResponseDTO update(UUID id,ProductRequestDTO dto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        existingProduct.setNome(dto.getNome());
        existingProduct.setDescricao(dto.getDescricao());
        existingProduct.setPreco(dto.getPreco());
        existingProduct.setCategoria(dto.getCategoria());
        existingProduct.setQuantidadeEmEstoque(dto.getQuantidadeEmEstoque());

        Product updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.toDTO(updatedProduct);
    }

    public void delete(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        productRepository.deleteById(id);
    }

    public ProductResponseDTO getById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ProductMapper.toDTO(product);
    }

    public List<ProductResponseDTO> getAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }
} 