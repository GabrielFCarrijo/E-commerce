package com.desario.gcarrijo.entity.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class OrderRequestDTO {
    private List<Item> itens;

    @Data
    public static class Item {
        private UUID productId;
        private Integer quantidade;
    }
} 