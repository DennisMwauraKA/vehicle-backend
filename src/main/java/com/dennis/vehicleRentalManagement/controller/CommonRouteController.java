package com.dennis.vehicleRentalManagement.controller;


import com.dennis.vehicleRentalManagement.commonfields.PageResponse;
import com.dennis.vehicleRentalManagement.dtos.BookVehicleRequest;
import com.dennis.vehicleRentalManagement.dtos.UpdateUserDto;
import com.dennis.vehicleRentalManagement.dtos.VehicleResponse;
import com.dennis.vehicleRentalManagement.entity.Booking;
import com.dennis.vehicleRentalManagement.entity.User;
import com.dennis.vehicleRentalManagement.service.BookingService;
import com.dennis.vehicleRentalManagement.service.UsersService;
import com.dennis.vehicleRentalManagement.service.VehicleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="General Routes")
public class CommonRouteController {
    private final VehicleService vehicleService;
    private final UsersService usersService;
    private final BookingService bookingService;

    public CommonRouteController(VehicleService vehicleService, UsersService usersService, BookingService bookingService) {
        this.vehicleService = vehicleService;
        this.usersService = usersService;
        this.bookingService = bookingService;
    }

    //view all vehicles in the database
    @GetMapping("/vehicles-listing")
    public ResponseEntity<PageResponse<VehicleResponse>> listAllVehicles(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                         @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        return ResponseEntity.ok(vehicleService.listAllVehicles(page, size));
    }

    // search vehicles by their properties eg, color,model
    @GetMapping("/search-vehicle")
    public ResponseEntity<PageResponse<VehicleResponse>> searchVehicle(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "hiringPrice", required = false) String hiringPrice,
            @RequestParam(name = "seatCapacity", required = false) Integer seatCapacity,
            @RequestParam(name = "fuelCapacity", required = false) String fuelCapacity,
            @RequestParam(name = "fuelType", required = false) String fuelType,
            @RequestParam(name = "engineType", required = false) String engineType

    ) {

        return ResponseEntity.ok(vehicleService.searchVehicle(page, size, model, color, hiringPrice, seatCapacity, fuelCapacity, fuelType, engineType));
    }

    // update user details
    @PutMapping("/update-user/{userId}")
    public ResponseEntity<Integer> updateUser(@PathVariable Integer userId,
                                           @Valid
                                           Authentication connectedUser,
                                           @RequestBody UpdateUserDto updateUserDto
    ) {
        return ResponseEntity.ok(usersService.updateUser(userId, connectedUser, updateUserDto));

    }

    //Book a vehicle
    @PostMapping("/book-vehicle")
    public ResponseEntity<Integer> bookVehicle(Authentication connectedUser, @RequestBody BookVehicleRequest bookVehicleRequest) {
        return ResponseEntity.ok(bookingService.bookVehicle(connectedUser,  bookVehicleRequest));

    }
// cancel a booking
@DeleteMapping("/cancel-booking/{bookingId}")
    public ResponseEntity<Integer> cancelBooking(@PathVariable Integer bookingId, Authentication connectedUser) {
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId,connectedUser));
}
}


