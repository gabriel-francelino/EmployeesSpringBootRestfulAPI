package com.gabriel.dto.order;

import com.gabriel.entity.Status;

public record ReadOrderDTO(Long id, String description, Status status, Long employee_id) {
}
