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
        Order orderBase1 = new Order("Munhequeira vermelha");
        Order orderBase2 = new Order("Colete de treino");
        orderBase1.setStatus(Status.IN_PROGRESS);
        orderBase2.setStatus(Status.IN_PROGRESS);

        return args -> {
            log.info("Preloading " + employeeRepository.save(new Employee("Georgian", "De Arrascaeta", "Jogador")));
            log.info("Preloadind " + employeeRepository.save(new Employee("Bruno", "Henrique", "Jogador")));

            log.info("Preloadind " + orderRepository.save(orderBase1));
            log.info("Preloadind " + orderRepository.save(orderBase2));
        };
    }
}
