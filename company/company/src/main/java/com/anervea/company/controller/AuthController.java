package com.anervea.company.controller;

import com.anervea.company.model.AnvrCompany;
import com.anervea.company.model.AnvrCompanyAcess;
import com.anervea.company.model.requests.ChangePasswordReuquest;
import com.anervea.company.model.requests.LoginRequest;
import com.anervea.company.model.requests.ResetPasswordRequest;
import com.anervea.company.model.response.CustomResponseMessage;
import com.anervea.company.model.response.EntityResponse;
import com.anervea.company.model.response.LoginResponse;
import com.anervea.company.config.TokenProvider;
import com.anervea.company.service.IAdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider jwtTokenUtil;
    @Autowired
    private IAdminUserService iAdminUser;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            final Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            final UserDetails candidateDetails = iAdminUser.loadUserByUsername(loginRequest.getEmail());
            AnvrCompany team = iAdminUser.findByEmail(candidateDetails.getUsername());

            if (!team.getIsActive() || team.getIsDeleted()) {
                throw new Exception("User Not Active ");
            }
            String token = "";
            try {
                token = jwtTokenUtil.generateToken(authentication);
            } catch (Exception e) {
                e.printStackTrace();
            }
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setCorporateId(team.getCompanyId());
            loginResponse.setName(team.getCompanyName());
            return new ResponseEntity<>(new EntityResponse(loginResponse, 0), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.UNAUTHORIZED);
        }
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            System.out.println("user "+username);
            System.out.println("password "+password);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new Exception("Please Check Username and Password", e);
        }

    }

    @PostMapping("changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordReuquest request){
        try{
            return new ResponseEntity<>(new EntityResponse(iAdminUser.changePassword(request,getUserFromToken()),0),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponseMessage(e.getMessage(),-1), HttpStatus.OK);
        }
    }

    @GetMapping("/sendOtp")
    public ResponseEntity<?> sendOtp(@RequestParam String userName){
        try{
            return new ResponseEntity<>(new EntityResponse(iAdminUser.sendOTP(userName),0),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponseMessage(e.getMessage(),-1), HttpStatus.OK);
        }
    }

    @PostMapping("/forgotVerifyPassword")
    public ResponseEntity<?> verifyOTPForForgotPassword(@RequestParam String email, @RequestParam int otp){
        try {
            return new ResponseEntity<>(new EntityResponse(iAdminUser.verifyOTPForForgotPassword(email, otp), 0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponseMessage(e.getMessage(), -1), HttpStatus.OK);
        }
    }


    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request){
        try {
            return new ResponseEntity<>(new EntityResponse(iAdminUser.resetPassword(request), 0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponseMessage(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    private AnvrCompanyAcess getUserFromToken() throws Exception {
        try {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        return iAdminUser.findByUserName(username);
       } catch (Exception e) {
           return iAdminUser.findByUserName("a@gmail.com");
       }
    }
}


