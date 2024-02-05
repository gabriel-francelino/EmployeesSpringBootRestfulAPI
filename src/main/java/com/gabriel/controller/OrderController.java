package com.gabriel.controller;

import com.gabriel.entity.Order;
import com.gabriel.hateoas.OrderModelAssembler;
import com.gabriel.service.OrderService;
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
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderModelAssembler assembler;

    @Autowired
    public OrderController(OrderService orderService, OrderModelAssembler assembler) {
        this.orderService = orderService;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Order order) {
        Order newOrder = orderService.create(order);
        EntityModel<Order> entityModel = assembler.toModel(newOrder);

        return ResponseEntity.created(entityModel
                        .getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<EntityModel<Order>> orders = orderService
                .getAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Order>> entityModels = CollectionModel.of(
                orders,
                linkTo(methodOn(OrderController.class).getAll()).withSelfRel()
        );
        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        EntityModel<Order> entityModel = assembler.toModel(order);
        return ResponseEntity.ok(entityModel);
    }
}
