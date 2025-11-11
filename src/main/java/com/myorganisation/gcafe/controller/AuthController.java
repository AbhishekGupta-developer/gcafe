package com.myorganisation.gcafe.controller;

import com.myorganisation.gcafe.dto.request.EmailOtpVerificationRequestDto;
import com.myorganisation.gcafe.dto.request.EmailRequestDto;
import com.myorganisation.gcafe.dto.response.GenericResponseDto;
import com.myorganisation.gcafe.enums.OtpPurpose;
import com.myorganisation.gcafe.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/signup/email/send-otp")
    public ResponseEntity<GenericResponseDto> sendOtpToSignup(@Valid @RequestBody EmailRequestDto emailRequestDto) {
        return new ResponseEntity<>(emailService.sendOtp(emailRequestDto, OtpPurpose.SIGNUP), HttpStatus.OK);
    }

    @PostMapping("/signup/email/verify-otp")
    public ResponseEntity<GenericResponseDto> verifyOtpToSignup(@Valid @RequestBody EmailOtpVerificationRequestDto emailOtpVerificationRequestDto) {
        return new ResponseEntity<>(emailService.verifyOtp(emailOtpVerificationRequestDto, OtpPurpose.SIGNUP), HttpStatus.CREATED);
    }

}
