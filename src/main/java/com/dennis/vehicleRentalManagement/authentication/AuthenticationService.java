package com.dennis.vehicleRentalManagement.authentication;

import com.dennis.vehicleRentalManagement.authorization.Role;
import com.dennis.vehicleRentalManagement.entity.User;
import com.dennis.vehicleRentalManagement.repository.UserRepository;
import com.dennis.vehicleRentalManagement.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegistrationDao registrationDao) {
        if (userRepository.findByEmail(registrationDao.getEmail()).isPresent()) {

            throw new IllegalStateException("email already in use");
        }
        // generate encrypted password
        String encryptedPassword = passwordEncoder.encode(registrationDao.getPassword());

        User user = User.builder().
                firstName(registrationDao.getFirstName())
                .lastName(registrationDao.getLastName())
                .email(registrationDao.getEmail())
                .password(encryptedPassword)
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }


    public AuthenticationResponse authenticate(@Valid AuthenticationDao request) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            if (authentication.isAuthenticated()) {
                User user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new IllegalStateException("User not found"));

                String token = jwtService.generateToken(user);

                return new AuthenticationResponse(token);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials", e);
        }
        return null;

    }


}
