package com.gabriel.service;

import com.gabriel.entity.Employee;
import com.gabriel.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.gabriel.common.EmployeeConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void createEmployee_WithValidData_ReturnEmployee() {
        when(employeeRepository.save(EMPLOYEE)).thenReturn(EMPLOYEE);

        Employee sut = employeeService.create(EMPLOYEE);

        assertThat(sut).isEqualTo(EMPLOYEE);
    }

    @Test
    public void createEmployee_WithInvalidData_ThrowsException(){
        when(employeeRepository.save(INVALID_EMPLOYEE)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> employeeService.create(INVALID_EMPLOYEE)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getById_ByExistingId_ReturnEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(EMPLOYEE));

        Employee sut = employeeService.getById(1L);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(EMPLOYEE);
    }

    @Test
    public void getById_ByUnexistingId_ThrowsException() {
        Long invalidId = -99L;
        when(employeeRepository.findById(invalidId)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> employeeService.delete(invalidId)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getAll_ReturnsAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(EMPLOYEES);

        List<Employee> sut = employeeService.getAll();

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(EMPLOYEE);
    }

    @Test
    public void getAll_ReturnsNoEmployees() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        List<Employee> sut = employeeService.getAll();

        assertThat(sut).isEmpty();
    }

    @Test
    public void deleteEmployee_WithExistingId_DoesNotThrowsAnyException() {
        assertThatCode(() -> employeeService.delete(1L)).doesNotThrowAnyException();
    }

    @Test
    public void deleteEmployee_WithUnexistingId_ThrowsException() {
        doThrow(new RuntimeException()).when(employeeRepository).deleteById(-99L);

        assertThatThrownBy(() -> employeeService.delete(-99L)).isInstanceOf(RuntimeException.class);
    }
}
