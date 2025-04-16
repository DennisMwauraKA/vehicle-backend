package com.dennis.vehicleRentalManagement.service;

import com.dennis.vehicleRentalManagement.authorization.Role;
import com.dennis.vehicleRentalManagement.commonfields.PageResponse;
import com.dennis.vehicleRentalManagement.dtos.BookVehicleRequest;
import com.dennis.vehicleRentalManagement.dtos.BookingResponse;
import com.dennis.vehicleRentalManagement.entity.Booking;
import com.dennis.vehicleRentalManagement.entity.User;
import com.dennis.vehicleRentalManagement.entity.Vehicle;
import com.dennis.vehicleRentalManagement.mapper.BookingMapper;
import com.dennis.vehicleRentalManagement.repository.BookingRepository;
import com.dennis.vehicleRentalManagement.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;
    private final BookingMapper bookingMapper;

    public BookingService(BookingRepository bookingRepository, VehicleRepository vehicleRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.vehicleRepository = vehicleRepository;
        this.bookingMapper = bookingMapper;
    }
// book a vehicle
    public Integer bookVehicle(Authentication connectedUser, BookVehicleRequest bookVehicleRequest) {
//check if vehicle exists

        Vehicle vehicle = vehicleRepository.findById(bookVehicleRequest.getVehicleId()).orElseThrow(() -> new EntityNotFoundException("No vehicle found"));

        // get the connected user

        User user = (User) connectedUser.getPrincipal();
        if(!user.getRole().equals(Role.ADMIN) &&!user.getId().equals(user.getId())){

            throw new RuntimeException("you cannot book a vehicle for another user");
        }
        // validate dates

        if (bookVehicleRequest.getStartDate() == null || bookVehicleRequest.getEndDate() == null) {
            throw new IllegalStateException("Both Start Date and End Date should be provided");
        }
        //the start date should be the first
        if (bookVehicleRequest.getStartDate().isAfter(bookVehicleRequest.getEndDate())) {
            throw new IllegalStateException("Start Date should be Before End Date");
        }

        // check the availability of the vehicle
        boolean isAvailable = bookingRepository.findDateAvailability(
                bookVehicleRequest.getVehicleId(),
                bookVehicleRequest.getStartDate(),
                bookVehicleRequest.getEndDate()
        ).isEmpty();
        if (!isAvailable) {
            throw new RuntimeException("Vehicle is not available in the specified days");
        }
        //calculate the price to be paid for the duration booked

        long days = ChronoUnit.DAYS.between(bookVehicleRequest.getStartDate(), bookVehicleRequest.getEndDate());
        double totalCost = days * vehicle.getHiringPrice();

        //create the booking
        Booking booking = Booking.builder()
                .vehicle(vehicle)
                .user(user)
                .startDate(bookVehicleRequest.getStartDate())
                .endDate(bookVehicleRequest.getEndDate())
                .totalCost(totalCost)
                .build();

         bookingRepository.save(booking);
         return booking.getId();
    }
 // cancel Vehicle booking
    public Integer cancelBooking(Integer bookingId, Authentication connectedUser) {
        // find id booking exists in the DB
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("No bookings found"));

        // get connected user
        User user = (User) connectedUser.getPrincipal();
        // make sure a user deletes their own booking
        if(!user.getRole().equals(Role.ADMIN) &&!user.getId().equals(user.getId())){

            throw new RuntimeException("you cannot cancel a vehicle booking for another user");
        }
        // cancel the booking of the vehicle
        bookingRepository.deleteById(bookingId);
        return bookingId;
    }

// get all bookings done
    public PageResponse<BookingResponse> viewAllBookings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookings = bookingRepository.findAll(pageable);
        List<BookingResponse> bookingResponse = bookings.stream().map(bookingMapper::toBookings).toList();

        return new PageResponse<>(
                bookingResponse,
                bookings.getNumber(),
                bookings.getSize(),
                bookings.getTotalElements(),
                bookings.getTotalPages(),
                bookings.isFirst(),
                bookings.isLast()
        );
    }
}
