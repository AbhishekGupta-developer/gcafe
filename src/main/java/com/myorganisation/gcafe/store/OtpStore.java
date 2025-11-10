package com.myorganisation.gcafe.store;

import java.util.concurrent.ConcurrentHashMap;

public class OtpStore {
    private static final ConcurrentHashMap<String, String> otpMap = new ConcurrentHashMap<>();

    public static void storeOtp(String email, String otp) {
        otpMap.put(email, otp);
    }

    public static String getOtp(String email) {
        return otpMap.get(email);
    }

    public static void clearOtp(String email) {
        otpMap.remove(email);
    }

}
