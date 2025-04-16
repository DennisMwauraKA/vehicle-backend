package com.dennis.vehicleRentalManagement.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    @Pattern(regexp = "\\d{4}-\\d{3}-\\d{3}")
    @Size(max = 10)
    private String phoneNumber;
    private String address;
    private String idNumber;
}
