package com.dennis.vehicleRentalManagement.dtos;

import com.dennis.vehicleRentalManagement.authorization.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersRequestDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String idNumber;
    private Role role;

}
