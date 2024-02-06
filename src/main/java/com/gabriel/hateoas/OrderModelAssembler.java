package com.gabriel.hateoas;

import com.gabriel.controller.OrderController;
import com.gabriel.entity.Order;
import com.gabriel.entity.Status;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {
    @Override
    public @NonNull EntityModel<Order> toModel(@NonNull Order order) {
        EntityModel<Order> orderModel = EntityModel.of(
                order,
                linkTo(methodOn(OrderController.class).getById(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAll()).withRel("orders")
        );

        if (order.getStatus() == Status.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete"));
        }

        return orderModel;
    }
}
