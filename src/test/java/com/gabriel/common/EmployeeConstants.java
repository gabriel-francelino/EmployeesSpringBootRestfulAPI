package com.gabriel.common;

import com.gabriel.dto.employee.RequestEmployeeDTO;
import com.gabriel.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeConstants {
    public static final Employee EMPLOYEE = new Employee(1L,"Test", "Test", "Test", new ArrayList<>());
    public static final Employee INVALID_EMPLOYEE = new Employee("", "", "");
    public static final RequestEmployeeDTO REQUEST_EMPLOYEE_DTO = new RequestEmployeeDTO("Test Test", "Test");
    public static final RequestEmployeeDTO INVALID_REQUEST_EMPLOYEE_DTO = new RequestEmployeeDTO("", "");

    public static final List<Employee> EMPLOYEES = new ArrayList<>() {
        {
            add(EMPLOYEE);
        }
    };
}
