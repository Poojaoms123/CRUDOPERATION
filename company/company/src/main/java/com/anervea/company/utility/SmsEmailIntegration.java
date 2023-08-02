package com.anervea.company.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    @Value("${AnvrCompanyLogo}")
    private String AnvrCompanyLogo;


   /* @Value("${twilio.accountSid}")
    private String twilioAccountSid;
    @Value("${twilio.authToken}")
    private String twilioAuthToken;
    @Value("${twilio.phoneNumber}")
    private String fromMobNo;*/

    public SmsEmailIntegration(JavaMailSender mailSender, String from,String twilioAccountSid, String twilioAuthToken, String fromMobNo){
        this.mailSender=mailSender;
        this.from=from;
    /*    this.twilioAccountSid=twilioAccountSid;
        this.twilioAuthToken=twilioAuthToken;
        this.fromMobNo=fromMobNo;*/
    }

    public void sendSms(String mobileNumber, String message) {
        try {
            String url = "http://smt.otptech.in/V2/http-api.php?apikey=JvCoXsoF5xO07fnd&number="+mobileNumber+"&senderid=RJMNTR&message="+ message+" &format=json";
            String result = restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendVerificationEmail(String email, String subject, String companyName, int otp) {

        try {
            String password="";

            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper mail = new MimeMessageHelper(mime);
            mail.setTo(email);
            mail.setSubject(subject);
            mail.setText(password,true);
            mail.setFrom(from);

            mailSender.send(mime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void sendEmail(String userName, String subject, String forgotPasswordTemplate) {

        System.out.println(" userName : "+userName);
        try {
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper mail = new MimeMessageHelper(mime);
            mail.setTo(userName);
            mail.setSubject(subject);
            mail.setText(forgotPasswordTemplate,true);
            mail.setFrom(from);
            mailSender.send(mime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

