package com.desario.gcarrijo.entity.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderResponseDTO {
    private UUID id;
    private String status;
    private LocalDateTime dataCriacao;
    private BigDecimal total;
    private List<OrderItemDTO> itens;
} 