package com.myorganisation.gcafe.util;

import java.security.SecureRandom;

public class OtpUtil {
    private static final SecureRandom random = new SecureRandom();
    private static final int OTP_LENGTH = 6;

    public static String generateOtp() {
        StringBuilder sb = new StringBuilder(OTP_LENGTH);
        for(int i = 0; i < OTP_LENGTH; i++) {
            sb.append(random.nextInt(10)); // 0-9 digits
        }

        return sb.toString();
    }

}
