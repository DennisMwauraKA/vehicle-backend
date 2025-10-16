package com.dennis.vehicleRentalManagement;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableMethodSecurity

public class VehicleRentalManagementApplication {


    public static void main(String[] args) {
        SpringApplication.run(VehicleRentalManagementApplication.class, args);
    }
}
