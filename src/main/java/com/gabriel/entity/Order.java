package com.gabriel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "customer_order")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "A descrição do pedido é obrigatória!")
    private String description;

    private Status status;

    @NonNull
    @ManyToOne @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Order order))
            return false;

        return Objects.equals(this.id, order.id) &&
                Objects.equals(this.description, order.description) &&
                this.status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.description, this.status);
    }
}
