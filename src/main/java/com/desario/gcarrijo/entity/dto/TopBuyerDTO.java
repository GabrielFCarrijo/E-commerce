package com.desario.gcarrijo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopBuyerDTO {
    private UUID userId;
    private String nome;
    private String email;
    private BigDecimal totalGasto;
}