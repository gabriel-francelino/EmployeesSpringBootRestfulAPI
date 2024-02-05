package com.gabriel.service;

import com.gabriel.entity.Order;
import com.gabriel.entity.Status;
import com.gabriel.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public Order create(Order newOrder) {
        newOrder.setStatus(Status.IN_PROGRESS);
        return orderRepository.save(newOrder);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getById(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Not found order with id" + id));
    }

    public Order cancel(Order canceledOrder) {
        canceledOrder.setStatus(Status.CANCELED);
        return orderRepository.save(canceledOrder);
    }

    public Order complete(Order completedOrder) {
        completedOrder.setStatus(Status.COMPLETED);
        return orderRepository.save(completedOrder);
    }
}
