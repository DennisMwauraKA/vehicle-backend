package com.dennis.vehicleRentalManagement;

import com.dennis.vehicleRentalManagement.authorization.Role;
import com.dennis.vehicleRentalManagement.entity.User;
import com.dennis.vehicleRentalManagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableMethodSecurity

public class VehicleRentalManagementApplication implements CommandLineRunner {


    private final UserRepository userRepository;
    @Value("${admin.password}")

    private String adminPassword;

    @Value("${admin.email}")
    private String email;

    @Value("${admin.firstname}")
    private String adminFirstName;


    @Value("${admin.lastname}")
    private String adminLastName;

    public static void main(String[] args) {
        SpringApplication.run(VehicleRentalManagementApplication.class, args);
    }

    @Override
    public void run(String... args) {

        User adminAccount = userRepository.findByRole(Role.ADMIN);
        if (adminAccount == null) {
            User user = new User();
            user.setEmail(email);
            user.setFirstName(adminFirstName);
            user.setLastName(adminLastName);
            user.setRole(Role.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode(adminPassword));
            userRepository.save(user);
        }



    }
}
