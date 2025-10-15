package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.dto.request.UserRequestDto;
import com.myorganisation.gcafe.dto.response.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserRequestDto userRequestDto);
    UserResponseDto getUserDetails(Long id);
}
