package com.gabriel.dto.order.mapper;

import com.gabriel.dto.order.CreateOrderDTO;
import com.gabriel.dto.order.ReadOrderDTO;
import com.gabriel.entity.Employee;
import com.gabriel.entity.Order;
import com.gabriel.exception.employee.EmployeeNotFoundException;
import com.gabriel.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    private final EmployeeRepository employeeRepository;

    public OrderMapper(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Order toOrder(CreateOrderDTO dto) {
        Employee employee = employeeRepository
                .findById(dto.employee_id())
                .orElseThrow(() -> new EmployeeNotFoundException(dto.employee_id()));

        return Order.builder()
                .description(dto.description())
                .employee(employee)
                .build();
    }

    public ReadOrderDTO toDTO(Order order) {
        return ReadOrderDTO.builder()
                .id(order.getId())
                .description(order.getDescription())
                .status(order.getStatus())
                .employee_id(order.getEmployee().getId())
                .build();
    }

}
