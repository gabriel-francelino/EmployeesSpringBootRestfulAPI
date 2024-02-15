package com.gabriel.dto.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestEmployeeDTO(
        @NotNull @NotBlank(message = "O atributo nome é obrigatório!") String name,
        @NotNull @NotBlank(message = "O papel do empregado é obrigatório!") String role) {
}
