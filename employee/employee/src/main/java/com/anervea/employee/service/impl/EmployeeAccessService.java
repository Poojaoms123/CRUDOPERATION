package com.anervea.employee.service.impl;

import com.anervea.employee.model.AnvrEmployee;
import com.anervea.employee.model.request.ChangePasswordRequest;
import com.anervea.employee.repository.AnvrEmployeeRepository;
import com.anervea.employee.service.IEmployeeAccessService;

import com.anervea.employee.utility.MessageService;
import com.anervea.employee.utility.OTPService;
import com.anervea.employee.utility.SmsEmailIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
public class EmployeeAccessService implements UserDetailsService, IEmployeeAccessService {
@Autowired
private AnvrEmployeeRepository anvrEmployeeRepository;

private String getEncodedPassword(String password) {
        return bcryptEncoder.encode(password);
    }

    @Autowired
    private SmsEmailIntegration smsEmailIntegration;
    @Autowired
    private MessageService messageService;
    @Autowired
    private OTPService otpService;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public Object sendOTP(String username) throws Exception {

        if (anvrEmployeeRepository.existsByEmailIdIgnoreCaseAndIsDeletedAndIsActive(username, false, true)) {
            String employeeName = anvrEmployeeRepository.getEmployeeNameByUserName(username);
            System.out.println(" employeeName : "+employeeName);
            otpService.clearOTP(username);
            int otp = otpService.generateOTP(username);
            smsEmailIntegration.sendVerificationEmail(username, "KollectCare forgot-Password Verification OTP", "Your OTP is " + otp + " , do not share anyone, this otp is valid upto 5 minutes. ");
            //String forgotPasswordTemplate = kollectCareHtmlTemplate.forgotPasswordTemplate(employeeName, otp);
            //smsEmailIntegration.sendVerificationEmail(username, "KollectCare forgot-Password Verification OTP", forgotPasswordTemplate);

            return "OTP send successfully on this email";
        } else {
            throw new Exception("This user is not valid");
        }


    }

    @Override
    public Object verifyOTPForForgotPassword(String username, int userOtp) throws Exception {

        if (anvrEmployeeRepository.existsByEmailIdIgnoreCaseAndIsDeletedAndIsActive(username, false, true)) {
            int generatedOTP = otpService.getOTP(username);
            if (userOtp == generatedOTP) {
                otpService.clearOTP(username);

                return "OTP verify successfully";
            } else {
                throw new Exception("OTP is incorrect");
            }
        } else {
            throw new Exception("This user is not valid");
        }
    }


    @Override
    public Object changeForgotPassword(ChangePasswordRequest request) throws Exception {

        if (anvrEmployeeRepository.existsByEmailIdIgnoreCaseAndIsDeletedAndIsActive(request.getUsername(), false, true)) {
            AnvrEmployee details = anvrEmployeeRepository.findByEmailIdIgnoreCaseAndIsDeletedAndIsActive(request.getUsername(), false, true);
            details.setPassword(getEncodedPassword(request.getNewPassword()));
            anvrEmployeeRepository.save(details);
            return "Your password changed Successfully";
        } else {
            throw new Exception("This user is invalid");
        }

    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AnvrEmployee user = anvrEmployeeRepository.findByEmailIdIgnoreCaseAndIsDeletedAndIsActive(s, false, true);
        if (user == null) {
            user = anvrEmployeeRepository.findByMobileNumber(s);
        }
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    getAuthority(user));
        } else {
            throw new UsernameNotFoundException("Invalid username");

        }
    }

    private Collection<? extends GrantedAuthority> getAuthority(AnvrEmployee user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + "User"));
        return authorities;
    }

    @Override
    public AnvrEmployee findByEmail(String username) {
        AnvrEmployee master = anvrEmployeeRepository.findByEmailIdIgnoreCaseAndIsDeletedAndIsActive(username, false, true);
        if (master ==null){
            master = anvrEmployeeRepository.findByMobileNumber(username);
        }
        return master;
    }

    @Override
    public void updateUserFcmToken(Long doctorId, String fcmToken) {
        try {
            if (anvrEmployeeRepository.existsById(doctorId)) {
                anvrEmployeeRepository.updateUserFcmToken(doctorId, fcmToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isMobileNumber(String mobileNumber) {
        return anvrEmployeeRepository.existsByMobileNumberAndIsDeletedAndIsActive(mobileNumber, false, true);
    }

    @Override
    public void loginWithMobileNumber(String mobileNumber) {
        if (anvrEmployeeRepository.existsByMobileNumberAndIsDeletedAndIsActive(mobileNumber, false, true)) {
            otpService.clearOTP(mobileNumber);
            int otp = otpService.generateOTP(mobileNumber);
            messageService.sendDoctorRegisterSmsWithOtp(mobileNumber, otp);
        }
    }

    @Override
    public Boolean verifyMobileLoginOtp(String mn, int userOtp) throws Exception {
        int generatedOTP = otpService.getOTP(mn);
        if (userOtp == generatedOTP) {
            otpService.clearOTP(mn);
            return true;
        } else {
            return false;
        }

    }
    @Override
    public Object logoutFcmToken(Long employeeId) {
        if (anvrEmployeeRepository.existsById(employeeId)) {
            anvrEmployeeRepository.setDoctorFcmToken(employeeId);
            return "logout successfully";
        } else {
            return "employee is not logined";
        }
    }
}
