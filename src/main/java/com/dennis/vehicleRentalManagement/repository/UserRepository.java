package com.dennis.vehicleRentalManagement.repository;

import com.dennis.vehicleRentalManagement.authorization.Role;
import com.dennis.vehicleRentalManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    User findByRole(Role role);
}
