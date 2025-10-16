package com.dennis.vehicleRentalManagement.configurations;

import com.dennis.vehicleRentalManagement.authorization.Role;
import com.dennis.vehicleRentalManagement.entity.User;
import com.dennis.vehicleRentalManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final UserRepository userRepository;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.email}")
    private String email;

    @Value("${admin.firstname}")
    private String adminFirstName;

    @Value("${admin.lastname}")
    private String adminLastName;

    @EventListener(ApplicationReadyEvent.class)
    public void createAdminIfMissing() {
        try {
            User admin = userRepository.findByRole(Role.ADMIN);
            if (admin == null) {
                User user = new User();
                user.setEmail(email);
                user.setFirstName(adminFirstName);
                user.setLastName(adminLastName);
                user.setRole(Role.ADMIN);
                user.setPassword(new BCryptPasswordEncoder().encode(adminPassword));
                userRepository.save(user);
                System.out.println(" Admin account created successfully!");
            } else {
                System.out.println(" Admin already exists, skipping creation.");
            }
        } catch (Exception e) {
            System.err.println(" Error creating admin account: " + e.getMessage());
        }
    }
}
