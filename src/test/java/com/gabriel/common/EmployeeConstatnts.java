package com.gabriel.common;

import com.gabriel.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeConstatnts {
    public static final Employee EMPLOYEE = new Employee(1L,"Test", "Test", "Test");
    public static final Employee INVALID_EMPLOYEE = new Employee("", "", "");

    public static final List<Employee> EMPLOYEES = new ArrayList<>() {
        {
            add(EMPLOYEE);
        }
    };
}
