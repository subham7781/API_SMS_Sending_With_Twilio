package com.twilioexample.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilioexample.payload.SmsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    @PostMapping("/send-sms")
    public ResponseEntity<String> sendSms(@RequestBody SmsRequest request) {
        try{
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                            new PhoneNumber(request.getTo()),
                            new PhoneNumber(twilioPhoneNumber),
                            request.getMessage())
                    .create();
            return ResponseEntity.ok("SMS sent successfully. SID: " + message.getSid());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to send SMS :"+e.getMessage());
        }

    }
}

