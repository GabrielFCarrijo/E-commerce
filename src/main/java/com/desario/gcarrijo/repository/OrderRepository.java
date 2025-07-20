package com.desario.gcarrijo.repository;

import com.desario.gcarrijo.entity.Order;
import com.desario.gcarrijo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUser(User user);
} 