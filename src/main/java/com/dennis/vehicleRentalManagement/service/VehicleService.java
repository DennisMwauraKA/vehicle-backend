package com.dennis.vehicleRentalManagement.service;

import com.dennis.vehicleRentalManagement.commonfields.PageResponse;
import com.dennis.vehicleRentalManagement.dtos.UpdateVehicleDto;
import com.dennis.vehicleRentalManagement.dtos.VehicleRegistrationRequest;
import com.dennis.vehicleRentalManagement.dtos.VehicleResponse;
import com.dennis.vehicleRentalManagement.entity.Vehicle;
import com.dennis.vehicleRentalManagement.mapper.VehicleMapper;
import com.dennis.vehicleRentalManagement.repository.BookingRepository;
import com.dennis.vehicleRentalManagement.repository.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;


    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper, BookingRepository bookingRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;


    }

    // register vehicles to the database
    public Integer registerVehicle(VehicleRegistrationRequest request) {

        try {
            Vehicle vehicle = vehicleMapper.toSaveVehicle(request);
            return vehicleRepository.save(vehicle).getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // remove a specific vehicle from the database
    public Integer deleteVehicle(Integer vehicleId) {
        // find if vehicle specified existsById and delete
        VehicleRepository vehicleRepository = this.vehicleRepository;

        try {
            if (vehicleRepository.existsById(vehicleId)) {
                vehicleRepository.deleteById(vehicleId);
                return vehicleId;

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return vehicleId;
    }

    // find all the vehicles registered in the system
    public PageResponse<VehicleResponse> listAllVehicles(int page, int size) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            Page<Vehicle> vehicles = vehicleRepository.findAll(pageable);
            List<VehicleResponse> vehicleResponse = vehicles.stream().map(vehicleMapper::toGetAllVehicles).toList();

            return new PageResponse<>(
                    vehicleResponse,
                    vehicles.getNumber(),
                    vehicles.getSize(),
                    vehicles.getTotalElements(),
                    vehicles.getTotalPages(),
                    vehicles.isFirst(),
                    vehicles.isLast()


            );
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }


    }

    // search functionality

    public PageResponse<VehicleResponse> searchVehicle(int page, int size, String model, String color, String hiringPrice, Integer seatCapacity, String fuelCapacity, String fuelType, String engineType) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            Page<Vehicle> vehicles = vehicleRepository.searchVehicle(pageable, model, color, hiringPrice, seatCapacity, fuelCapacity, fuelType, engineType);
            List<VehicleResponse> vehicleResponse = vehicles.stream().map(vehicleMapper::toGetAllVehicles).toList();

            return new PageResponse<>(
                    vehicleResponse,
                    vehicles.getNumber(),
                    vehicles.getSize(),
                    vehicles.getTotalElements(),
                    vehicles.getTotalPages(),
                    vehicles.isFirst(),
                    vehicles.isLast()


            );
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    //update vehicle
    public Vehicle updateVehicle(Integer vehicleId, @Valid UpdateVehicleDto updateVehicleDto) {

        // check if the  vehicle exists in the database
        try {
            Vehicle existingVehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new RuntimeException("Vehicle not found"));
//
            if (updateVehicleDto.getColor() != null) {
                existingVehicle.setColor(updateVehicleDto.getColor());
            }
            if (updateVehicleDto.getSeatCapacity() != null) {
                existingVehicle.setSeatCapacity(updateVehicleDto.getSeatCapacity());
            }
            if (updateVehicleDto.getHiringPrice() != null) {
                existingVehicle.setHiringPrice(updateVehicleDto.getHiringPrice());
            }
            existingVehicle.setAvailable(updateVehicleDto.isAvailable());
            return vehicleRepository.save(existingVehicle);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }


    }


}
