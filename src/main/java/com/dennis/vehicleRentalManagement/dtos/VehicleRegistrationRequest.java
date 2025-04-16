package com.dennis.vehicleRentalManagement.dtos;




public record VehicleRegistrationRequest(

        Integer id,
        String model,
        String color,
        Double hiringPrice,
        Integer seatCapacity,
        String fuelCapacity,
        String fuelType,
        String engineType,
        boolean available

) {
}
