package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.dto.request.EmailAndPasswordRequestDto;
import com.myorganisation.gcafe.dto.request.SigninRequestDto;
import com.myorganisation.gcafe.dto.response.GenericResponseDto;

public interface AuthService {
    GenericResponseDto signup(String authHeader, EmailAndPasswordRequestDto emailAndPasswordRequestDto);
    GenericResponseDto signin(SigninRequestDto signinRequestDto);
    GenericResponseDto resetPassword(String authHeader, EmailAndPasswordRequestDto emailAndPasswordRequestDto);
}
