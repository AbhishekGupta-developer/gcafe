package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.constants.UserConstants;
import com.myorganisation.gcafe.dto.request.EmailOtpVerificationRequestDto;
import com.myorganisation.gcafe.dto.request.EmailRequestDto;
import com.myorganisation.gcafe.dto.response.GenericResponseDto;
import com.myorganisation.gcafe.enums.OtpPurpose;
import com.myorganisation.gcafe.exception.InvalidOtpException;
import com.myorganisation.gcafe.model.User;
import com.myorganisation.gcafe.repository.UserRepository;
import com.myorganisation.gcafe.store.OtpStore;
import com.myorganisation.gcafe.util.JwtUtil;
import com.myorganisation.gcafe.util.OtpUtil;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public GenericResponseDto sendOtp(EmailRequestDto emailRequestDto, OtpPurpose otpPurpose) {
        String email = emailRequestDto.getEmail();
        User user = userRepository.findByEmail(email).orElse(null);

        if(otpPurpose == OtpPurpose.SIGNUP) {
            if(user != null && user.isActive()) {
                return GenericResponseDto.builder()
                        .success(false)
                        .message("Email is already registered.")
                        .build();
            }
        } else if(otpPurpose == OtpPurpose.PASSWORD_RESET) {
            if(user == null || !user.isActive()) {
                return GenericResponseDto.builder()
                        .success(false)
                        .message("Email doesn't exist or account is inactive.")
                        .build();
            }
        }

        String otp = OtpUtil.generateOtp();
        OtpStore.storeOtp(email, otp);

        String subject = "GCafe - " + (otpPurpose == OtpPurpose.SIGNUP ? "Signup OTP" : "Password Reset OTP");

        String purposeMessage = "<h1>Your OTP is " + otp + "</h1>";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(purposeMessage, true);

            mailSender.send(mimeMessage);
        } catch(Exception e) {
            System.out.println("An exception occurred during mail sending: " + e.getMessage());
            return GenericResponseDto.builder()
                    .success(false)
                    .message("Failed to send OTP email. Please try again later.")
                    .build();
        }

        return GenericResponseDto.builder()
                .success(true)
                .message("OTP send to " + email)
                .detail(Map.of("purpose", otpPurpose.name()))
                .build();
    }

    @Override
    public GenericResponseDto verifyOtp(EmailOtpVerificationRequestDto emailOtpVerificationRequestDto, OtpPurpose otpPurpose) {
        String email = emailOtpVerificationRequestDto.getEmail();
        String otp = emailOtpVerificationRequestDto.getOtp();

        String storedOtp = OtpStore.getOtp(email);

        if(storedOtp != null && storedOtp.equals(otp)) {
            OtpStore.clearOtp(email);

            if(otpPurpose == OtpPurpose.SIGNUP) {

                if (!userRepository.existsByEmail(email)) {
                    User user = User.builder()
                            .email(email)
                            .isEmailVerified(true)
                            .password(UserConstants.PASSWORD_NOT_SET)
                            .active(false)
                            .build();

                    userRepository.save(user);
                }

                String signupToken = jwtUtil.generateSignupToken(email);

                return GenericResponseDto.builder()
                        .success(true)
                        .message("OTP verified")
                        .detail(Map.of("signupToken", signupToken))
                        .build();
            } else if(otpPurpose == OtpPurpose.PASSWORD_RESET) {
                User user = userRepository.findByEmail(email).orElse(null);

                if(user != null && user.isActive()) {
                    String passwordResetToken = jwtUtil.generatePasswordResetToken(email);

                    return GenericResponseDto.builder()
                            .success(true)
                            .message("OTP verified")
                            .detail(Map.of("passwordResetToken", passwordResetToken))
                            .build();
                }
            }
        }

        throw new InvalidOtpException("OTP verification failed");
    }
}
