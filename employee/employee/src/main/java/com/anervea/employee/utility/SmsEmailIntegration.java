package com.anervea.employee.utility;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Component
@RequiredArgsConstructor
public class SmsEmailIntegration {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    @Value("${twilio.accountSid}")
    private String twilioAccountSid;
    @Value("${twilio.authToken}")
    private String twilioAuthToken;
    @Value("${twilio.phoneNumber}")
    private String fromMobNo;

    public SmsEmailIntegration(JavaMailSender mailSender, String from,String twilioAccountSid, String twilioAuthToken, String fromMobNo){
        this.mailSender=mailSender;
        this.from=from;
        this.twilioAccountSid=twilioAccountSid;
        this.twilioAuthToken=twilioAuthToken;
        this.fromMobNo=fromMobNo;
    }




    public void sendVerificationEmail(String email, String subject, String forgotPasswordTemplate) {
        System.out.println("email to: "+email);
        try {
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper mail = new MimeMessageHelper(mime);
            mail.setTo(email);
            mail.setSubject(subject);
            mail.setText(forgotPasswordTemplate,true);
            mail.setFrom(from);
            mailSender.send(mime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


