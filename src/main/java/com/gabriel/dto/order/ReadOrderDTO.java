package com.gabriel.dto.order;

import com.gabriel.entity.Status;
import lombok.Builder;

@Builder
public record ReadOrderDTO(Long id, String description, Status status, Long employee_id) {
}
