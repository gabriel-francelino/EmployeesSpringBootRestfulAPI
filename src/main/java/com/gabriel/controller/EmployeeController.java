package com.gabriel.controller;

import com.gabriel.entity.Employee;
import com.gabriel.hateoas.EmployeeModelAssembler;
import com.gabriel.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    private final EmployeeModelAssembler assembler;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeModelAssembler assembler) {
        this.employeeService = employeeService;
        this.assembler = assembler;
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @PostMapping
    public EntityModel<Employee> create(@RequestBody Employee employee){
        Employee newEmployee = employeeService.create(employee);
        return assembler.toModel(newEmployee);
    }

    @GetMapping
    public CollectionModel<EntityModel<Employee>> getAll(){
        List<EntityModel<Employee>> employees = employeeService
                .getAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList()
                );

        return CollectionModel.of(
                employees,
                linkTo(methodOn(EmployeeController.class).getAll()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public EntityModel<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return assembler.toModel(employee);
    }

    @PutMapping("/{id}")
    public EntityModel<Employee> update(@RequestBody Employee employee, @PathVariable Long id){
        Employee updatedEmployee = employeeService.update(employee, id);
        return assembler.toModel(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        employeeService.delete(id);
    }
}
