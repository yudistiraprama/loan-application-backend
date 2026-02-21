package com.loan.loanapp.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {

    public void sendSms(String phoneNumber, String message) {

        System.out.println("=================================");
        System.out.println("SMS SENT");
        System.out.println("To: " + phoneNumber);
        System.out.println("Message: " + message);
        System.out.println("=================================");

    }
}