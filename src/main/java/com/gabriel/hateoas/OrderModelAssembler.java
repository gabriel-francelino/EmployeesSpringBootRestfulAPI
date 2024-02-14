package com.gabriel.hateoas;

import com.gabriel.controller.OrderController;
import com.gabriel.dto.order.ReadOrderDTO;
import com.gabriel.entity.Order;
import com.gabriel.entity.Status;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<ReadOrderDTO, EntityModel<ReadOrderDTO>> {
    @Override
    public @NonNull EntityModel<ReadOrderDTO> toModel(@NonNull ReadOrderDTO order) {
        EntityModel<ReadOrderDTO> orderModel = EntityModel.of(
                order,
                linkTo(methodOn(OrderController.class).getById(order.id())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAll()).withRel("orders")
        );

        if (order.status() == Status.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(OrderController.class).cancel(order.id())).withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderController.class).complete(order.id())).withRel("complete"));
        }

        return orderModel;
    }
}
