package com.anervea.company.service.impl;


import com.anervea.company.model.AnvrCompany;
import com.anervea.company.model.AnvrCompanyAcess;
import com.anervea.company.model.requests.ChangePasswordReuquest;
import com.anervea.company.model.requests.ResetPasswordRequest;
import com.anervea.company.repository.AnvrCompanyAcessRepository;
import com.anervea.company.repository.AnvrCompanyRepository;
import com.anervea.company.service.IAdminUserService;
import com.anervea.company.utility.SmsEmailIntegration;
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
public class AdminUserService implements UserDetailsService, IAdminUserService {

    @Autowired
    private AnvrCompanyAcessRepository anvrCompanyAcessRepository;
    @Autowired
    private AnvrCompanyRepository anvrCompanyRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

     AnvrCompanyAcess user =anvrCompanyAcessRepository.findByUserNameIgnoreCase(email);

        if (user == null) {
            throw new UsernameNotFoundException("Login not enable for this user or Invalid username, contact to Tattvan team");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName() , user.getPassword(),
                getAuthority(user));
    }

    private Collection<? extends GrantedAuthority> getAuthority(AnvrCompanyAcess user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + "Admin"));
        return authorities;
    }

    @Override
    public AnvrCompany findByEmail(String username) throws Exception {

        AnvrCompany master =anvrCompanyRepository.getByCorporateByUsername(username, false);
        if (master ==null){
            throw new Exception("Username Invalid");
        }

        return master;
    }


    @Override
    public AnvrCompanyAcess findByUserName(String username) throws Exception {
        AnvrCompanyAcess master = anvrCompanyAcessRepository.getByCorporateByUsername(username, false);
        if (master ==null){
            throw new Exception("Username Invalid");
        }
        return master;
    }



    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    private String getEncodedPassword(String password) {
        return bcryptEncoder.encode(password);
    }


    @Override
    public Object changePassword(ChangePasswordReuquest request, AnvrCompanyAcess userFromToken) throws Exception {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (anvrCompanyAcessRepository.existsByCompanyId(userFromToken.getCompanyId())) {
                AnvrCompanyAcess byEmail = anvrCompanyAcessRepository.findByUserName(userFromToken.getUserName());
                if (encoder.matches(request.getOldPassword(), byEmail.getPassword())) {
                    byEmail.setPassword(encoder.encode(request.getPassword()));
                    anvrCompanyAcessRepository.save(byEmail);
                    return "Password Changed";
                } else {
                    throw new Exception("incorrect old password");
                }
            } else {
                return "Company not exists";
            }
        }

    @Autowired
    private SmsEmailIntegration smsEmailIntegration;

    @Autowired
    private OTPService otpService;
    @Override
    public Object sendOTP(String userName) throws Exception {
        if (anvrCompanyAcessRepository.existsByUserNameIgnoreCase(userName)){

            String companyName =anvrCompanyAcessRepository.getCorporateNameByUserName(userName);
            System.out.println(" companyName : "+companyName);
            otpService.clearOTP(userName);
            int otp = otpService.generateOTP(userName);
            smsEmailIntegration.sendEmail(userName, "Anervea forgot-Password Verification OTP ", "OTP "+otp);

            return "OTP send Successfully on this email";
        }else {
            throw new Exception("This Corporate is not valid");
        }
    }

    @Override
    public Object verifyOTPForForgotPassword(String email, int otp) throws Exception {
        if (anvrCompanyAcessRepository.existsByUserNameIgnoreCase(email)) {
            AnvrCompanyAcess details = anvrCompanyAcessRepository.findByUserName(email);

            int generatedOTP = otpService.getOTP(email);
            if (otp == generatedOTP) {
                otpService.clearOTP(email);

                return details.getCompanyId();
            } else {
                throw new Exception("-1");
            }
        } else {
            throw new Exception("this Company is not valid");
        }

    }

    @Override
    public Object resetPassword(ResetPasswordRequest request){
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (anvrCompanyAcessRepository.existsByCompanyId(request.getCompanyId())){
                System.out.println("in iff->");
                 AnvrCompanyAcess details =anvrCompanyAcessRepository.findByCompanyId(request.getCompanyId());
                System.out.println("in iff->1");
                details.setPassword(encoder.encode(request.getNewPassword()));
                    System.out.println("password : "+details.getPassword());
                   anvrCompanyAcessRepository.save(details);
                    return "Password Changed ";
            }else {
                return "Company not exists";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
