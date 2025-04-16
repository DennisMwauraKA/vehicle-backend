package com.dennis.vehicleRentalManagement.mapper;

import com.dennis.vehicleRentalManagement.dtos.UsersRequestDto;
import com.dennis.vehicleRentalManagement.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {


    public UsersRequestDto toUsers(User user) {
        return UsersRequestDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .idNumber(user.getIdNumber())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }
}
