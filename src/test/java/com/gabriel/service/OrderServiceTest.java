package com.gabriel.service;

import com.gabriel.dto.order.ReadOrderDTO;
import com.gabriel.dto.order.mapper.OrderMapper;
import com.gabriel.exception.order.OrderNotFoundException;
import com.gabriel.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

    @Test
    public void getOrder_WithExistingId_ReturnOrderDTO() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(ORDER));
        when(orderMapper.toDTO(ORDER)).thenReturn(READ_ORDER_DTO);

        ReadOrderDTO sut = orderService.getById(1L);

        assertThat(sut).isNotNull();
        assertThat(sut).isInstanceOf(ReadOrderDTO.class);
        assertThat(sut).isEqualTo(READ_ORDER_DTO);
    }

    @Test
    public void getOrder_WithUnexistingId_ThrowsException() {
        when(orderRepository.findById(-99L)).thenThrow(OrderNotFoundException.class);

        assertThatThrownBy(() -> orderService.getById(-99L)).isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    public void cancelOrder_StatusInProgress_ReturnCanceledOrderDTO() {
        when(orderRepository.save(CANCELED_ORDER)).thenReturn(CANCELED_ORDER);
        when(orderMapper.toDTO(CANCELED_ORDER)).thenReturn(READ_CANCELED_ORDER_DTO);

        ReadOrderDTO sut = orderService.cancel(ORDER);

        assertThat(sut.status()).isEqualByComparingTo(CANCELED_ORDER.getStatus());
    }

    @Test
    public void completeOrder_StatusInProgress_ReturnCompletedOrderDTO() {
        when(orderRepository.save(COMPLETED_ORDER)).thenReturn(COMPLETED_ORDER);
        when(orderMapper.toDTO(COMPLETED_ORDER)).thenReturn(READ_COMPLETED_ORDER_DTO);

        ReadOrderDTO sut = orderService.complete(ORDER);

        assertThat(sut.status()).isEqualByComparingTo(COMPLETED_ORDER.getStatus());
    }

}
