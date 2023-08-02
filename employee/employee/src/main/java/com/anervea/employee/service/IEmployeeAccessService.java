package com.anervea.employee.service;


import com.anervea.employee.model.AnvrEmployee;
import com.anervea.employee.model.request.ChangePasswordRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface IEmployeeAccessService {

    Object sendOTP(String email) throws Exception;

    Object verifyOTPForForgotPassword(String email, int otp) throws Exception;

    Object changeForgotPassword(ChangePasswordRequest request) throws Exception;

    UserDetails loadUserByUsername(String email);

    AnvrEmployee findByEmail(String username);

    void updateUserFcmToken(Long id, String fcmToken);

    boolean isMobileNumber(String mobileNumber);

    void loginWithMobileNumber(String mobileNumber);

    Boolean verifyMobileLoginOtp(String mobileNumber, int mobileOtp) throws Exception;

    Object logoutFcmToken(Long employeeId);
}
