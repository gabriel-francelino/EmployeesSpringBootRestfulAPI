package com.gabriel.common;

import com.gabriel.dto.order.CreateOrderDTO;
import com.gabriel.dto.order.ReadOrderDTO;
import com.gabriel.entity.Order;
import com.gabriel.entity.Status;

import static com.gabriel.common.EmployeeConstants.EMPLOYEE;

public class OrderConstants {
    public static final CreateOrderDTO CREATE_ORDER_DTO = new CreateOrderDTO("Test", 1L);
    public static final CreateOrderDTO INVALID_CREATE_ORDER_DTO = new CreateOrderDTO("", null);
    public static final ReadOrderDTO READ_ORDER_DTO = new ReadOrderDTO(1L, "Test", Status.IN_PROGRESS, 1L);
    public static final Order ORDER = new Order(1L, "Test", Status.IN_PROGRESS, EMPLOYEE);
    public static final Order INVALID_ORDER = new Order(null, "", null, EMPLOYEE);
}
