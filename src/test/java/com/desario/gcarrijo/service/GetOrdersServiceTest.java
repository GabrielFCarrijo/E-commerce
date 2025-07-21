package com.desario.gcarrijo.service;

import com.desario.gcarrijo.entity.Order;
import com.desario.gcarrijo.entity.User;
import com.desario.gcarrijo.entity.dto.OrderResponseDTO;
import com.desario.gcarrijo.entity.mapper.OrderMapper;
import com.desario.gcarrijo.repository.OrderRepository;
import com.desario.gcarrijo.service.order.GetOrdersService;
import com.desario.gcarrijo.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith( MockitoExtension.class)
class GetOrdersServiceTest {

    @InjectMocks
    private GetOrdersService getOrdersService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserUtil userUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrdersForCurrentUser() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .nome("Usuário Teste")
                .email("teste@email.com")
                .build();

        Order order = Order.builder()
                .id(null)
                .status(Order.Status.PAGO)
                .dataCriacao(LocalDateTime.now())
                .total(new BigDecimal("150.00"))
                .user(user)
                .itens(Collections.emptyList())
                .build();

        when(userUtil.getUser()).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(List.of(order));

        List<OrderResponseDTO> result = getOrdersService.getOrdersForCurrentUser();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("150.00"), result.get(0).getTotal());
    }

    @Test
    void testGetAllOrders() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .nome("Usuário Teste")
                .email("teste@email.com")
                .build();

        Order order = Order.builder()
                .status(Order.Status.PENDENTE)
                .dataCriacao(LocalDateTime.now())
                .total(new BigDecimal("99.99"))
                .user(user)
                .itens(Collections.emptyList())
                .build();

        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<OrderResponseDTO> result = getOrdersService.getAllOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("PENDENTE", result.get(0).getStatus());
    }
}
