package com.desario.gcarrijo.service.order;

import com.desario.gcarrijo.config.exceptions.OrderNotFoundException;
import com.desario.gcarrijo.entity.Order;
import com.desario.gcarrijo.entity.OrderItem;
import com.desario.gcarrijo.entity.Product;
import com.desario.gcarrijo.entity.dto.OrderResponseDTO;
import com.desario.gcarrijo.entity.mapper.OrderMapper;
import com.desario.gcarrijo.repository.OrderRepository;
import com.desario.gcarrijo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PayOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public OrderResponseDTO payOrder(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (order.getStatus() != Order.Status.PENDENTE) {
            throw new OrderNotFoundException("Only pending orders can be paid");
        }

        for (OrderItem item : order.getItens()) {
            Product product = item.getProduct();
            if (product.getQuantidadeEmEstoque() < item.getQuantidade()) {
                order.setStatus(Order.Status.CANCELADO);
                orderRepository.save(order);
                throw new OrderNotFoundException("Insufficient stock for product: " + product.getNome());
            }
        }

        List<Product> updatedProducts = new ArrayList<>();
        for (OrderItem item : order.getItens()) {
            Product product = item.getProduct();
            product.setQuantidadeEmEstoque(product.getQuantidadeEmEstoque() - item.getQuantidade());
            updatedProducts.add(product);
        }
        productRepository.saveAll(updatedProducts);

        order.setStatus(Order.Status.PAGO);
        orderRepository.save(order);

        return OrderMapper.toDTO(order);
    }
}
