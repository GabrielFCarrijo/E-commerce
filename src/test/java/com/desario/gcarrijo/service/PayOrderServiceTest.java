package com.desario.gcarrijo.service;

import com.desario.gcarrijo.config.exceptions.OrderNotFoundException;
import com.desario.gcarrijo.entity.Order;
import com.desario.gcarrijo.entity.OrderItem;
import com.desario.gcarrijo.entity.Product;
import com.desario.gcarrijo.entity.dto.OrderResponseDTO;
import com.desario.gcarrijo.repository.OrderRepository;
import com.desario.gcarrijo.repository.ProductRepository;
import com.desario.gcarrijo.service.order.PayOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayOrderServiceTest {

    @InjectMocks
    private PayOrderService payOrderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    private UUID orderId;
    private Product product;
    private OrderItem orderItem;
    private Order order;

    @BeforeEach
    void setup() {
        orderId = UUID.randomUUID();

        product = Product.builder()
                .id(UUID.randomUUID())
                .nome("Produto Teste")
                .quantidadeEmEstoque(10)
                .preco(new BigDecimal("100.00"))
                .build();

        orderItem = OrderItem.builder()
                .id(UUID.randomUUID())
                .product(product)
                .quantidade(2)
                .build();

        order = Order.builder()
                .id(orderId)
                .status(Order.Status.PENDENTE)
                .itens(List.of(orderItem))
                .build();
    }

    @Test
    void testPayOrderSuccess() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(productRepository.saveAll(any())).thenReturn(List.of(product));
        when(orderRepository.save(any())).thenReturn(order);

        OrderResponseDTO result = payOrderService.payOrder(orderId);

        assertNotNull(result);
        assertEquals(Order.Status.PAGO, order.getStatus());
        verify(productRepository).saveAll(any());
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void testPayOrder_NotFound() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> payOrderService.payOrder(orderId));
    }

    @Test
    void testPayOrder_NotPendingStatus() {
        order.setStatus(Order.Status.CANCELADO);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(OrderNotFoundException.class, () -> payOrderService.payOrder(orderId));
    }

    @Test
    void testPayOrder_InsufficientStock() {
        product.setQuantidadeEmEstoque(1);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenReturn(order);

        OrderNotFoundException ex = assertThrows(OrderNotFoundException.class, () -> payOrderService.payOrder(orderId));
        assertTrue(ex.getMessage().contains("Insufficient stock"));

        verify(orderRepository).save(any());
        assertEquals(Order.Status.CANCELADO, order.getStatus());
    }
}
