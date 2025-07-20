package com.desario.gcarrijo.service.order;

import com.desario.gcarrijo.config.exceptions.OrderNotFoundException;
import com.desario.gcarrijo.entity.*;
import com.desario.gcarrijo.entity.dto.OrderRequestDTO;
import com.desario.gcarrijo.entity.dto.OrderResponseDTO;
import com.desario.gcarrijo.entity.mapper.OrderMapper;
import com.desario.gcarrijo.repository.OrderRepository;
import com.desario.gcarrijo.repository.ProductRepository;
import com.desario.gcarrijo.repository.UserRepository;
import com.desario.gcarrijo.util.OrderUtils;
import com.desario.gcarrijo.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserUtil userUtil;

    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        User user = userUtil.getUser();

        List<UUID> productIds = dto.getItens().stream()
                .map(OrderRequestDTO.Item::getProductId)
                .collect(Collectors.toList());

        List<Product> products = productRepository.findAllById(productIds);
        if (products.size() != productIds.size()) {
            throw new OrderNotFoundException("One or more products not found");
        }

        Map<UUID, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        List<OrderItem> orderItems = OrderUtils.buildOrderItems(dto.getItens(), productMap);
        BigDecimal total = OrderUtils.calculateTotal(orderItems);

        Order order = buildOrder(total,user,orderItems);

        orderItems.forEach(item -> item.setOrder(order));
        return OrderMapper.toDTO(orderRepository.save(order));
    }

    private static Order buildOrder (BigDecimal total,User user,List<OrderItem> orderItems) {
        return Order.builder()
                .status(Order.Status.PENDENTE)
                .dataCriacao(LocalDateTime.now())
                .total(total)
                .user(user)
                .itens(orderItems)
                .build();
    }
}

