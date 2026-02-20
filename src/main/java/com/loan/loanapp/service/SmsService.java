package com.loan.loanapp.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {

    public void sendSms(String phoneNumber, String message) {

        // Mock implementation
        System.out.println("=================================");
        System.out.println("📱 SMS SENT");
        System.out.println("To: " + phoneNumber);
        System.out.println("Message: " + message);
        System.out.println("=================================");

        // Di production, ini tempat integrasi:
        // Twilio, AWS SNS, Nexmo, dll
    }
}