package com.gabriel.service;

import com.gabriel.dto.order.CreateOrderDTO;
import com.gabriel.dto.order.mapper.OrderMapper;
import com.gabriel.entity.Employee;
import com.gabriel.entity.Order;
import com.gabriel.entity.Status;
import com.gabriel.exception.EmployeeNotFoundException;
import com.gabriel.exception.OrderNotFoundException;
import com.gabriel.repository.EmployeeRepository;
import com.gabriel.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper){
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public Order create(CreateOrderDTO orderDTO) {
        Order newOrder = orderMapper.toOrder(orderDTO);

        newOrder.setStatus(Status.IN_PROGRESS);

        return orderRepository.save(newOrder);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getById(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
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
