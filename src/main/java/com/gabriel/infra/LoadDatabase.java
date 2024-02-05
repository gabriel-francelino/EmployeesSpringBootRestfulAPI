package com.gabriel.infra;

import com.gabriel.entity.Employee;
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
    CommandLineRunner initDabase(EmployeeRepository employeeRepository) {
        return args -> {
            log.info("Preloading " + employeeRepository.save(new Employee("Georgian", "De Arrascaeta", "Jogador")));
            log.info("Preloadind " + employeeRepository.save(new Employee("Bruno", "Henrique", "Jogador")));
        };
    }
}
