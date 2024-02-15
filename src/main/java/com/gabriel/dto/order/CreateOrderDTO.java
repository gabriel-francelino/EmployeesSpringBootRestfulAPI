package com.gabriel.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOrderDTO(
        @NotNull @NotBlank(message = "A descrição é obrigatória!") String description,
        @NotNull(message = "É necessário informar o empregado!") Long employee_id) {
}
