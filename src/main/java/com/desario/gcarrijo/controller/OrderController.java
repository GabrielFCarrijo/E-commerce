package com.desario.gcarrijo.controller;

import com.desario.gcarrijo.entity.dto.OrderRequestDTO;
import com.desario.gcarrijo.entity.dto.OrderResponseDTO;
import com.desario.gcarrijo.service.order.GetOrdersService;
import com.desario.gcarrijo.service.order.OrderService;
import com.desario.gcarrijo.service.order.PayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService createOrderService;

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private GetOrdersService getOrdersService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO dto) {
        OrderResponseDTO response = createOrderService.createOrder(dto);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{uuid}/pay")
    public ResponseEntity<OrderResponseDTO> payOrder(@PathVariable("uuid") UUID id) {
        OrderResponseDTO response = payOrderService.payOrder(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersForCurrentUser() {
        List<OrderResponseDTO> response = getOrdersService.getOrdersForCurrentUser();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> response = getOrdersService.getAllOrders();
        return ResponseEntity.ok(response);
    }
}

