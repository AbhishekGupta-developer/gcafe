package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.constants.UserConstants;
import com.myorganisation.gcafe.dto.request.EmailAndPasswordRequestDto;
import com.myorganisation.gcafe.dto.request.SigninRequestDto;
import com.myorganisation.gcafe.dto.response.GenericResponseDto;
import com.myorganisation.gcafe.exception.UserNotFoundException;
import com.myorganisation.gcafe.model.User;
import com.myorganisation.gcafe.repository.UserRepository;
import com.myorganisation.gcafe.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Override
    public GenericResponseDto signup(String authHeader, EmailAndPasswordRequestDto emailAndPasswordRequestDto) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return GenericResponseDto.builder()
                    .success(false)
                    .message("Missing or malformed token")
                    .detail(null)
                    .build();
        }

        String signupToken = authHeader.substring(7);

        if(signupToken.isEmpty() || !jwtUtil.isValidSignupToken(signupToken)) {
            return GenericResponseDto.builder()
                    .success(false)
                    .message("Invalid or expired signup token")
                    .detail(null)
                    .build();
        }

        String emailFromToken = jwtUtil.extractEmail(signupToken);
        if(!emailFromToken.equals(emailAndPasswordRequestDto.getEmail())) {
            return GenericResponseDto.builder()
                    .success(false)
                    .message("Email mismatch")
                    .detail(null)
                    .build();
        }

        User user = userRepository.findByEmail(emailAndPasswordRequestDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User email: " + emailAndPasswordRequestDto.getEmail() + " doesn't exist"));

        if(!user.isEmailVerified()) {
            return GenericResponseDto.builder()
                    .success(false)
                    .message("Email not verified")
                    .detail(null)
                    .build();
        }

        if(user.getPassword() != null && !UserConstants.PASSWORD_NOT_SET.equals(user.getPassword())) {
            return GenericResponseDto.builder()
                    .success(false)
                    .message("Password already set")
                    .detail(null)
                    .build();
        }

        user.setPassword(passwordEncoder.encode(emailAndPasswordRequestDto.getPassword()));
        user.setActive(true);
        userRepository.save(user);

        return GenericResponseDto.builder()
                .success(true)
                .message("Signup complete")
                .detail(null)
                .build();
    }

    @Override
    public GenericResponseDto signin(SigninRequestDto signinRequestDto) {
        User user = userRepository.findByEmail(signinRequestDto.getEmail())
                .orElse(null);

        if(user == null) {
            return GenericResponseDto.builder()
                    .success(false)
                    .message("Invalid credentials")
                    .detail(null)
                    .build();
        }

        if(!user.isActive()) {
            return GenericResponseDto.builder()
                    .success(false)
                    .message("Account not active or blocked")
                    .detail(null)
                    .build();
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequestDto.getEmail(), signinRequestDto.getPassword())
            );
        } catch(AuthenticationException e) {
            return GenericResponseDto.builder()
                    .success(false)
                    .message("Invalid credentials")
                    .detail(null)
                    .build();
        }

        if(!user.isEmailVerified()) {
            return GenericResponseDto.builder()
                    .success(false)
                    .message("Email not verified")
                    .detail(null)
                    .build();
        }

        String authToken = jwtUtil.generateAuthToken(user.getEmail());

        emailService.sendSigninAlert(signinRequestDto.getEmail());

        return GenericResponseDto.builder()
                .success(true)
                .message("Signin successful")
                .detail(Map.of("authToken", authToken))
                .build();
    }

    @Override
    public GenericResponseDto resetPassword(String authHeader, EmailAndPasswordRequestDto emailAndPasswordRequestDto) {
        return null;
    }
}
