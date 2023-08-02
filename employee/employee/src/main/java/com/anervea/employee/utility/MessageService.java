package com.anervea.employee.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

@Component
public class MessageService {


    public void sendDoctorRegisterSmsWithOtp(String mobileNumber, int otp) {
        try {
            System.out.println("In sms mobileNumber: "+mobileNumber);
            String smsUrl="http://125.16.147.178/VoicenSMS/webresources/CreateSMSCampaignGet?ukey=OwDQR52tNn1CezjD7X7pBjW1m&msisdn=" + mobileNumber + "&language=0&credittype=7&senderid=GRGHLT&templateid=0&" +
                    "message=Your Login OTP for the KollectCare Platform is "+otp+"&filetype=2";
            System.out.println("smsUrl:"+smsUrl);
            RestTemplate restTemplate=new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            HttpEntity httpEntity = new HttpEntity(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(smsUrl, HttpMethod.GET, httpEntity, String.class);
            System.out.println("After responseEntity:");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    }
