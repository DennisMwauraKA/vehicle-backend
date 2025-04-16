package com.dennis.vehicleRentalManagement.dtos;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
private Integer id;
private String model;
private String color;
private String hiringPrice;
private Integer seatCapacity;
private String fuelCapacity;
private String fuelType;
private String engineType;
private boolean available;
}
