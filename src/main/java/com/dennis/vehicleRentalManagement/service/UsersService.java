package com.dennis.vehicleRentalManagement.service;

import com.dennis.vehicleRentalManagement.authorization.Role;
import com.dennis.vehicleRentalManagement.commonfields.PageResponse;
import com.dennis.vehicleRentalManagement.dtos.UpdateUserDto;
import com.dennis.vehicleRentalManagement.dtos.UsersRequestDto;
import com.dennis.vehicleRentalManagement.entity.User;
import com.dennis.vehicleRentalManagement.mapper.UserMapper;
import com.dennis.vehicleRentalManagement.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UsersService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // get all users registered
    public PageResponse<UsersRequestDto> viewAllUsers(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        List<UsersRequestDto> content = users.stream().map(userMapper::toUsers).toList();

        return new PageResponse<>(
                content,
                users.getNumber(),
                users.getSize(),
                users.getNumberOfElements(),
                users.getTotalPages(),
                users.isFirst(),
                users.isLast()
        );
    }


    // update user details
    public Integer updateUser(Integer userId, Authentication connectedUser, UpdateUserDto  updateUserDto) {


        try{
            // check if user exists in the database
            User existingUser= userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
            // get the current authenticated user
            User user = ((User) connectedUser.getPrincipal());
            //check if user is an admin or the owner of the account to update the  account
            if (!user.getRole().equals(Role.ADMIN) && !user.getId().equals(userId)) {
                throw new RuntimeException("You can not update another user's details");
            }


            existingUser.setAddress(updateUserDto.getAddress());
            existingUser.setIdNumber(updateUserDto.getIdNumber());
            existingUser.setPhoneNumber(updateUserDto.getPhoneNumber());

            return userRepository.save(existingUser).getId();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
}
