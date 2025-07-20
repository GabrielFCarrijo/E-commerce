package com.desario.gcarrijo.entity.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequestDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoria;
    private Integer quantidadeEmEstoque;
} 