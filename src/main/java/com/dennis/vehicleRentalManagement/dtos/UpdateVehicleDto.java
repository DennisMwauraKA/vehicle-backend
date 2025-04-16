package com.dennis.vehicleRentalManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVehicleDto {
    private Integer id;
    private String color;
    private Double hiringPrice;
    private Integer seatCapacity;
    private boolean available;
    private String updateField;
}
