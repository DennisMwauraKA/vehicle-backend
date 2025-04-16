package com.dennis.vehicleRentalManagement.entity;

import com.dennis.vehicleRentalManagement.commonfields.ParentEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity

public class Vehicle extends ParentEntity {

    private String model;
    private String color;
    private Double hiringPrice;
    private Integer seatCapacity;
    private String fuelCapacity;
    private String fuelType;
    private String engineType;
    private boolean available;
 @OneToMany(mappedBy = "vehicle")
  private List<Booking> bookings;
}
