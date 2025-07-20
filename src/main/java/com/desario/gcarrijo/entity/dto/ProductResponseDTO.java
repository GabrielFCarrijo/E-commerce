package com.desario.gcarrijo.entity.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductResponseDTO {
    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoria;
    private Integer quantidadeEmEstoque;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
} 