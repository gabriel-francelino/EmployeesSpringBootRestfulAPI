package com.gabriel.service;

import com.gabriel.dto.order.CreateOrderDTO;
import com.gabriel.dto.order.ReadOrderDTO;
import com.gabriel.dto.order.mapper.OrderMapper;
import com.gabriel.entity.Order;
import com.gabriel.entity.Status;
import com.gabriel.exception.OrderNotFoundException;
import com.gabriel.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper){
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public ReadOrderDTO create(CreateOrderDTO orderDTO) {
        Order newOrder = orderMapper.toOrder(orderDTO);
        newOrder.setStatus(Status.IN_PROGRESS);

        return orderMapper.toDTO(orderRepository.save(newOrder));
    }

    public List<ReadOrderDTO> getAll() {
        return orderRepository
                .findAll()
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ReadOrderDTO getById(Long id) {
        return orderRepository
                .findById(id)
                .map(orderMapper::toDTO)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order getOrderById(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public ReadOrderDTO cancel(Order canceledOrder) {
        canceledOrder.setStatus(Status.CANCELED);
        return orderMapper.toDTO(orderRepository.save(canceledOrder));
    }

    public ReadOrderDTO complete(Order completedOrder) {
        completedOrder.setStatus(Status.COMPLETED);
        return orderMapper.toDTO(orderRepository.save(completedOrder));
    }
}
