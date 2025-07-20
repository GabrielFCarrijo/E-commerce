package com.desario.gcarrijo.entity.mapper;

import com.desario.gcarrijo.entity.Order;
import com.desario.gcarrijo.entity.OrderItem;
import com.desario.gcarrijo.entity.dto.OrderItemDTO;
import com.desario.gcarrijo.entity.dto.OrderResponseDTO;

import java.util.UUID;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponseDTO toDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setStatus(order.getStatus().name());
        dto.setDataCriacao(order.getDataCriacao());
        dto.setTotal(order.getTotal());
        dto.setItens(order.getItens().stream()
                .map(OrderMapper::toItemDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    public static OrderItemDTO toItemDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setProductId(item.getProduct().getId());
        dto.setNomeProduto(item.getProduct().getNome());
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoUnitario(item.getPrecoUnitario());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }
}
