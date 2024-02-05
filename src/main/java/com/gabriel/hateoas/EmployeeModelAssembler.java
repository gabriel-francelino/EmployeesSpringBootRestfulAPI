package com.gabriel.hateoas;

import com.gabriel.controller.EmployeeController;
import com.gabriel.entity.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {
    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        return EntityModel.of(
                employee,
                linkTo(methodOn(EmployeeController.class).getById(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getAll()).withRel("employees")
        );
    }
}
