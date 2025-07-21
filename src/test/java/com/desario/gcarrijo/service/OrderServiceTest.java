package com.desario.gcarrijo.service;

import com.desario.gcarrijo.config.exceptions.OrderNotFoundException;
import com.desario.gcarrijo.entity.*;
import com.desario.gcarrijo.entity.dto.OrderRequestDTO;
import com.desario.gcarrijo.entity.dto.OrderRequestDTO.Item;
import com.desario.gcarrijo.entity.dto.OrderResponseDTO;
import com.desario.gcarrijo.repository.OrderRepository;
import com.desario.gcarrijo.repository.ProductRepository;
import com.desario.gcarrijo.util.UserUtil;
import com.desario.gcarrijo.service.order.OrderService;
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
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserUtil userUtil;

    private UUID productId;
    private Product product;
    private User user;

    @BeforeEach
    void setup() {
        productId = UUID.randomUUID();

        product = new Product();
        product.setId(productId);
        product.setNome("Produto Teste");
        product.setPreco(new BigDecimal("10.00"));
        product.setQuantidadeEmEstoque(100);

        user = new User();
        user.setId(UUID.randomUUID());
        user.setNome("Gabriel");
        user.setEmail("gabriel@example.com");
        user.setSenha("senha");
        user.setRole(User.Role.USER);
    }

    @Test
    void testCreateOrderSuccess() {
        OrderRequestDTO.Item item = new OrderRequestDTO.Item();
        item.setProductId(productId);
        item.setQuantidade(2);

        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setItens(List.of(item));

        when(userUtil.getUser()).thenReturn(user);
        when(productRepository.findAllById(any())).thenReturn(List.of(product));
        when(orderRepository.save(any())).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(UUID.randomUUID());
            return order;
        });

        OrderResponseDTO response = orderService.createOrder(dto);

        assertNotNull(response);
        assertEquals("PENDENTE", response.getStatus());
        assertEquals(new BigDecimal("20.00"), response.getTotal());
        assertEquals(1, response.getItens().size());

        verify(productRepository).findAllById(any());
        verify(orderRepository).save(any());
    }

    @Test
    void testCreateOrderProductNotFound() {
        OrderRequestDTO.Item item = new OrderRequestDTO.Item();
        item.setProductId(productId);
        item.setQuantidade(1);

        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setItens(List.of(item));

        when(userUtil.getUser()).thenReturn(user);
        when(productRepository.findAllById(any())).thenReturn(Collections.emptyList());

        assertThrows(OrderNotFoundException.class, () -> orderService.createOrder(dto));
    }
}
