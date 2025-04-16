package com.dennis.vehicleRentalManagement.mapper;

import com.dennis.vehicleRentalManagement.dtos.BookingResponse;
import com.dennis.vehicleRentalManagement.entity.Booking;
import org.springframework.stereotype.Service;

@Service
public class BookingMapper {
    public BookingResponse toBookings(Booking booking) {
        return BookingResponse.builder()
                .vehicleModel(booking.getVehicle().getModel())
                .vehicleColor(booking.getVehicle().getColor())
                .vehicleHiringPrice(booking.getVehicle().getHiringPrice())
                .userPhoneNumber(booking.getUser().getPhoneNumber())
                .userEmail(booking.getUser().getEmail())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .totalCost(booking.getTotalCost())
                .build();
    }
}
