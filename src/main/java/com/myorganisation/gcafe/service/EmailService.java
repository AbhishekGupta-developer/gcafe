package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.dto.request.EmailOtpVerificationRequestDto;
import com.myorganisation.gcafe.dto.request.EmailRequestDto;
import com.myorganisation.gcafe.dto.response.GenericResponseDto;
import com.myorganisation.gcafe.enums.OtpPurpose;

public interface EmailService {
    GenericResponseDto sendOtp(EmailRequestDto emailRequestDto, OtpPurpose otpPurpose);
    GenericResponseDto verifyOtp(EmailOtpVerificationRequestDto emailOtpVerificationRequestDto, OtpPurpose otpPurpose);
}
