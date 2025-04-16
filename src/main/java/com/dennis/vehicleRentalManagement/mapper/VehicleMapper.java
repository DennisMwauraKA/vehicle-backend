package com.dennis.vehicleRentalManagement.mapper;

import com.dennis.vehicleRentalManagement.dtos.VehicleRegistrationRequest;
import com.dennis.vehicleRentalManagement.dtos.VehicleResponse;
import com.dennis.vehicleRentalManagement.entity.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class VehicleMapper {

    public Vehicle toSaveVehicle(VehicleRegistrationRequest request) {

        return Vehicle.builder()


                .model(request.model())
                .color(request.color())
                .hiringPrice(request.hiringPrice())
                .seatCapacity(request.seatCapacity())
                .fuelCapacity(request.fuelCapacity())
                .fuelType(request.fuelType())
                .engineType(request.engineType())
                .available(true)


                .build();
    }


    public VehicleResponse toGetAllVehicles(Vehicle vehicle) {

        return VehicleResponse.builder()
                .id(vehicle.getId())
                .model(vehicle.getModel())
                .color(vehicle.getColor())
                .hiringPrice(String.valueOf(vehicle.getHiringPrice()))
                .seatCapacity(vehicle.getSeatCapacity())
                .fuelType(vehicle.getFuelType())
                .fuelCapacity(vehicle.getFuelCapacity())
                .engineType(vehicle.getEngineType())
                .available(vehicle.isAvailable())


                .build();
    }
}
