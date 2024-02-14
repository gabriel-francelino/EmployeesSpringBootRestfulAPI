package com.gabriel.controller;

import com.gabriel.dto.order.CreateOrderDTO;
import com.gabriel.entity.Order;
import com.gabriel.entity.Status;
import com.gabriel.hateoas.OrderModelAssembler;
import com.gabriel.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<?> create(@Valid @RequestBody CreateOrderDTO order) {
        Order newOrder = orderService.create(order);
        EntityModel<Order> entityModel = assembler.toModel(newOrder);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
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

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id){
        Order order = orderService.getById(id);

        if(order.getStatus() == Status.IN_PROGRESS) {
            Order completedOrder = orderService.complete(order);
            return ResponseEntity.ok(assembler.toModel(completedOrder));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't complete an order that is in the " +
                                order.getStatus() + " status")
                );
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        Order order = orderService.getById(id);

        if (order.getStatus() == Status.IN_PROGRESS) {
            Order canceledOrder = orderService.cancel(order);
            return ResponseEntity.ok(assembler.toModel(canceledOrder));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't cancel an order that is in the " +
                                order.getStatus() + " status")
                );
    }
}
