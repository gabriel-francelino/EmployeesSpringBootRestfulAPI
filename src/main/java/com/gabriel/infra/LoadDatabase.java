package com.gabriel.infra;

import com.gabriel.entity.Employee;
import com.gabriel.entity.Order;
import com.gabriel.entity.Status;
import com.gabriel.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.gabriel.repository.EmployeeRepository;


@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        Employee employee1 = new Employee("Georgian", "De Arrascaeta", "Jogador");
        Employee employee2 = new Employee("Bruno", "Henrique", "Jogador");

        Order orderBase1 = new Order("Munhequeira vermelha", employee1);
        Order orderBase2 = new Order("Colete de treino", employee2);
        orderBase1.setStatus(Status.IN_PROGRESS);
        orderBase2.setStatus(Status.IN_PROGRESS);

        return args -> {
            log.info("Preloading " + employeeRepository.save(employee1));
            log.info("Preloadind " + employeeRepository.save(employee2));

            log.info("Preloadind " + orderRepository.save(orderBase1));
            log.info("Preloadind " + orderRepository.save(orderBase2));
        };
    }
}
