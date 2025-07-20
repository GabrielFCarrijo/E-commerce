package com.desario.gcarrijo.entity.mapper;

import com.desario.gcarrijo.entity.Product;
import com.desario.gcarrijo.entity.dto.ProductRequestDTO;
import com.desario.gcarrijo.entity.dto.ProductResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto) {
        return Product.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .categoria(dto.getCategoria())
                .quantidadeEmEstoque(dto.getQuantidadeEmEstoque())
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();
    }

    public static ProductResponseDTO toDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setNome(product.getNome());
        dto.setDescricao(product.getDescricao());
        dto.setPreco(product.getPreco());
        dto.setCategoria(product.getCategoria());
        dto.setQuantidadeEmEstoque(product.getQuantidadeEmEstoque());
        dto.setDataCriacao(product.getDataCriacao());
        dto.setDataAtualizacao(product.getDataAtualizacao());
        return dto;
    }

    public static List<ProductResponseDTO> toDTOList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static void updateEntity(Product product, ProductRequestDTO dto) {
        product.setNome(dto.getNome());
        product.setDescricao(dto.getDescricao());
        product.setPreco(dto.getPreco());
        product.setCategoria(dto.getCategoria());
        product.setQuantidadeEmEstoque(dto.getQuantidadeEmEstoque());
        product.setDataAtualizacao(LocalDateTime.now());
    }
}
