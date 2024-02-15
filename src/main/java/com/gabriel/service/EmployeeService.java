package com.gabriel.service;

import com.gabriel.dto.employee.RequestEmployeeDTO;
import com.gabriel.dto.employee.mapper.EmployeeMapper;
import com.gabriel.entity.Employee;
import com.gabriel.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Service;
import com.gabriel.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public Employee create(RequestEmployeeDTO employee) {
        Employee newEmployee = employeeMapper.toEmployee(employee);
        return employeeRepository.save(newEmployee);
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getById(Long id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee update(RequestEmployeeDTO updatedEmployee, Long id) {
        return employeeRepository
                .findById(id)
                .map(employee -> {
                    employee.setName(updatedEmployee.name());
                    employee.setRole(updatedEmployee.role());
                    return employeeRepository.save(employee);
                })
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public void delete(Long id)  {
//        Optional<Employee> employee = Optional.ofNullable(employeeRepository
//                .findById(id)
//                .orElseThrow(() -> new EmployeeNotFoundException(id)));

//        if (employee.isEmpty()) {
//            throw new EmployeeNotFoundException(id);
//        }

        employeeRepository.deleteById(id);
    }
}
