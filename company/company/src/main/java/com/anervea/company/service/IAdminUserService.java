package com.anervea.company.service;

import com.anervea.company.model.AnvrCompany;
import com.anervea.company.model.AnvrCompanyAcess;
import com.anervea.company.model.requests.ChangePasswordReuquest;

import com.anervea.company.model.requests.ResetPasswordRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAdminUserService {
    UserDetails loadUserByUsername(String email);

    AnvrCompany findByEmail(String username) throws Exception;

    Object changePassword(ChangePasswordReuquest request, AnvrCompanyAcess userFromToken) throws Exception;

    AnvrCompanyAcess  findByUserName(String username) throws Exception;

    Object sendOTP(String userName) throws Exception;

    Object verifyOTPForForgotPassword(String email, int otp) throws Exception;

    Object resetPassword(ResetPasswordRequest request);
}
