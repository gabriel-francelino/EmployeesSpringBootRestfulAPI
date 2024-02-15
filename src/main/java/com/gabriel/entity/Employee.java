package com.gabriel.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gabriel.exception.employee.InvalidNameException;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "first_name", nullable = false)
    private String firstName;

//    @NotBlank(message = "O ultimo nome é obrigatório")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Order> orders;

    public Employee(String firstName, String lastName, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.orders = new ArrayList<>();
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public void setName(String name) {
        String[] parts = name.split(" ");
        if (parts.length < 2) throw new InvalidNameException();
        this.firstName = parts[0];
        this.lastName = parts[1];
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Employee employee)) return false;

        return Objects.equals(this.id, employee.id) &&
                Objects.equals(this.firstName, employee.firstName) &&
                Objects.equals(this.lastName, employee.lastName) &&
                Objects.equals(this.role, employee.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.role);
    }
}
