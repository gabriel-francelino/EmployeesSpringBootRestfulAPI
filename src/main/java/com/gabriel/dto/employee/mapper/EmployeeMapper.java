package com.gabriel.dto.employee.mapper;

import com.gabriel.dto.employee.RequestEmployeeDTO;
import com.gabriel.entity.Employee;
import com.gabriel.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EmployeeMapper {
    private final EmployeeRepository employeeRepository;

    public EmployeeMapper(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee toEmployee(RequestEmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setName(dto.name());
        employee.setRole(dto.role());
        employee.setOrders(new ArrayList<>());
        return employeeRepository.save(employee);
    }
}
