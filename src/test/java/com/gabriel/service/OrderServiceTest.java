package com.gabriel.service;

import com.gabriel.dto.order.ReadOrderDTO;
import com.gabriel.dto.order.mapper.OrderMapper;
import com.gabriel.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gabriel.common.OrderConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Test
    public void createOrder_WithValidData_ReturnOrderDTO() {
        when(orderMapper.toOrder(CREATE_ORDER_DTO)).thenReturn(ORDER);
        when(orderMapper.toDTO(ORDER)).thenReturn(READ_ORDER_DTO);
        when(orderRepository.save(ORDER)).thenReturn(ORDER);

        ReadOrderDTO sut = orderService.create(CREATE_ORDER_DTO);

        assertThat(sut).isInstanceOf(ReadOrderDTO.class);
        assertThat(sut).isEqualTo(READ_ORDER_DTO);
    }

    @Test
    public void createOrder_WithInvalidData_ThroesException() {
        when(orderMapper.toOrder(INVALID_CREATE_ORDER_DTO)).thenReturn(INVALID_ORDER);
        when(orderRepository.save(INVALID_ORDER)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> orderService.create(INVALID_CREATE_ORDER_DTO))
                .isInstanceOf(RuntimeException.class);
    }
}
