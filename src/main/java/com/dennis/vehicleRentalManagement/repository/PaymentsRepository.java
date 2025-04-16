package com.dennis.vehicleRentalManagement.repository;

import com.dennis.vehicleRentalManagement.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments,Integer> {
}
