package com.dennis.vehicleRentalManagement.repository;


import com.dennis.vehicleRentalManagement.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    @Query("""
             SELECT v from Vehicle v WHERE
              LOWER(v.model) LIKE LOWER(CONCAT('%',:model,'%')) OR\s
              LOWER(v.color) LIKE LOWER(CONCAT('%',:color,'%')) OR\s
              v.hiringPrice=:hiringPrice OR\s
              v.seatCapacity=:seatCapacity OR
              LOWER(v.fuelCapacity) LIKE LOWER(CONCAT('%',:fuelCapacity,'%')) OR\s
               LOWER(v.fuelType) LIKE LOWER(CONCAT('%',:fuelType,'%')) OR\s
               LOWER(v.engineType) LIKE LOWER(CONCAT('%',:engineType,'%'))\s
            \s"""


    )
    Page<Vehicle> searchVehicle(Pageable pageable,
                                String model,
                                String color,
                                String hiringPrice,
                                Integer seatCapacity,
                                String fuelCapacity,
                                String fuelType,
                                String engineType


    );


}
