package com.desario.gcarrijo.util;

import com.desario.gcarrijo.entity.OrderItem;
import com.desario.gcarrijo.entity.Product;
import com.desario.gcarrijo.entity.dto.OrderRequestDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderUtils {

    public static List<OrderItem> buildOrderItems(List<OrderRequestDTO.Item> itemDTOs, Map<UUID, Product> productMap) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderRequestDTO.Item dto : itemDTOs) {
            Product product = productMap.get(dto.getProductId());

            if (product == null || product.getQuantidadeEmEstoque() < dto.getQuantidade()) {
                throw new RuntimeException("Produto sem estoque suficiente: " + dto.getProductId());
            }

            BigDecimal subtotal = product.getPreco().multiply(BigDecimal.valueOf(dto.getQuantidade()));
            OrderItem item = OrderItem.builder()
                    .product(product)
                    .quantidade(dto.getQuantidade())
                    .precoUnitario(product.getPreco())
                    .subtotal(subtotal)
                    .build();

            orderItems.add(item);
        }

        return orderItems;
    }

    public static BigDecimal calculateTotal (List<OrderItem> itens) {
        return itens.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
