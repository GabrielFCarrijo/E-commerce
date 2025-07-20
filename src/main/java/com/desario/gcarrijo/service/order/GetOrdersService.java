package com.desario.gcarrijo.service.order;

import com.desario.gcarrijo.entity.Order;
import com.desario.gcarrijo.entity.User;
import com.desario.gcarrijo.entity.dto.OrderResponseDTO;
import com.desario.gcarrijo.entity.mapper.OrderMapper;
import com.desario.gcarrijo.repository.OrderRepository;
import com.desario.gcarrijo.repository.UserRepository;
import com.desario.gcarrijo.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetOrdersService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserUtil userUtil;

    public List<OrderResponseDTO> getOrdersForCurrentUser() {
        User user = userUtil.getUser();

        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }
}
