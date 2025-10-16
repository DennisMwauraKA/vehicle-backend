package com.dennis.vehicleRentalManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


   @ManyToOne
   @JoinColumn(name = "users_id")
    private User user;

   @ManyToOne
   @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalCost;

}
