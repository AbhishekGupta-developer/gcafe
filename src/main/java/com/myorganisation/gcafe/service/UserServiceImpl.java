package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.dto.request.UserRequestDto;
import com.myorganisation.gcafe.dto.response.UserResponseDto;
import com.myorganisation.gcafe.exception.UserNotFoundException;
import com.myorganisation.gcafe.model.User;
import com.myorganisation.gcafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setRole(userRequestDto.getRole());
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        userRepository.save(user);

        return mapUserToUserResponseDto(user);
    }

    @Override
    public UserResponseDto getUserDetails(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User id: " + id + " doesn't exist"));

        return mapUserToUserResponseDto(user);
    }

    // Helper methods
    // Map User to UserResponseDto
    private UserResponseDto mapUserToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setRole(user.getRole());
        userResponseDto.setUsername(user.getUsername());

        return userResponseDto;
    }
}
