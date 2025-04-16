package com.dennis.vehicleRentalManagement.repository;

import com.dennis.vehicleRentalManagement.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {


    @Query("SELECT b FROM Booking b WHERE b.vehicle.id = :vehicleId AND " +
            "(:startDate BETWEEN b.startDate AND b.endDate OR " +
            ":endDate BETWEEN b.startDate AND b.endDate OR " +
            "(b.startDate BETWEEN :startDate AND :endDate))")
    List<Booking> findDateAvailability(
            @Param("vehicleId") Integer vehicleId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}