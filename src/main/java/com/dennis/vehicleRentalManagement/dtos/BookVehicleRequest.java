package com.dennis.vehicleRentalManagement.dtos;

import com.dennis.vehicleRentalManagement.entity.User;

import com.dennis.vehicleRentalManagement.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookVehicleRequest {
    private Integer userId;
    private Integer vehicleId;
    private LocalDate startDate;
    private LocalDate endDate;

}
