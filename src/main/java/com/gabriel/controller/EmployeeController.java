package com.gabriel.controller;

import com.gabriel.entity.Employee;
import com.gabriel.hateoas.EmployeeModelAssembler;
import com.gabriel.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> create(@RequestBody Employee employee){
        Employee newEmployee = employeeService.create(employee);
        EntityModel<Employee> entityModel = assembler.toModel(newEmployee);
        return ResponseEntity.created(entityModel
                .getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<EntityModel<Employee>> employees = employeeService
                .getAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList()
                );

        CollectionModel<EntityModel<Employee>> entityModels = CollectionModel.of(
                employees,
                linkTo(methodOn(EmployeeController.class).getAll()).withSelfRel()
        );

        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        EntityModel<Employee> entityModel = assembler.toModel(employee);
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Employee employee, @PathVariable Long id){
        Employee updatedEmployee = employeeService.update(employee, id);
        EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
