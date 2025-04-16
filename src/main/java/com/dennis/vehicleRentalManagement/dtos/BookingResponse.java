package com.dennis.vehicleRentalManagement.dtos;

import com.dennis.vehicleRentalManagement.entity.User;
import com.dennis.vehicleRentalManagement.entity.Vehicle;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class BookingResponse {

    private String vehicleModel;
    private String vehicleColor;
    private Double vehicleHiringPrice;
    private String userPhoneNumber;
    private String userEmail;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalCost;
}
