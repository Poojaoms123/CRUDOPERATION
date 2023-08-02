package com.anervea.employee.controller;


import com.anervea.employee.config.TokenProvider;
import com.anervea.employee.model.AnvrEmployee;
import com.anervea.employee.model.request.ChangePasswordRequest;
import com.anervea.employee.model.request.LoginRequest;
import com.anervea.employee.model.request.MobileAuthenticationRequest;
import com.anervea.employee.model.response.CustomResponseMessage;
import com.anervea.employee.model.response.EntityResponse;
import com.anervea.employee.model.response.LoginResponse;
import com.anervea.employee.repository.AnvrCompanyRepository;
import com.anervea.employee.repository.AnvrEmployeeRepository;
import com.anervea.employee.service.IEmployeeAccessService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Api(value = "Anervea Employee Authentication ", description = "anervea Authentication ", tags = {"Anervea  Authentication"})

public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider jwtTokenUtil;
    @Autowired
    private IEmployeeAccessService iEmployeeAccessService;
    @Value("${fileBaseUrl}")
    private String fileBaseUrl;
    @Autowired
    private AnvrCompanyRepository corporateCompanyRepository;


    @PostMapping("/sendOTP")//send otp
    public ResponseEntity<?> sendOTP(@RequestParam String email) {
        try {
            return new ResponseEntity<>(new EntityResponse(iEmployeeAccessService.sendOTP(email), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponseMessage(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @PostMapping("/verifyOTPForForgotPassword")
    public ResponseEntity<?> verifyOTPForForgotPassword(@RequestParam String email, @RequestParam int otp) {
        try {
            return new ResponseEntity<>(new EntityResponse(iEmployeeAccessService.verifyOTPForForgotPassword(email, otp), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponseMessage(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @PostMapping("/changeForgotPassword")
    public ResponseEntity<?> changeForgotPassword(@RequestBody ChangePasswordRequest request) {
        try {
            return new ResponseEntity<>(new EntityResponse(iEmployeeAccessService.changeForgotPassword(request), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponseMessage(e.getMessage(), -1), HttpStatus.OK);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            final Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            final UserDetails candidateDetails = iEmployeeAccessService.loadUserByUsername(loginRequest.getEmail());
           AnvrEmployee user = iEmployeeAccessService.findByEmail(candidateDetails.getUsername());

            if (!user.getIsActive() || user.getIsDeleted()) {
                throw new Exception("User is not active, Please contact to administrator");
            }
            final String token = jwtTokenUtil.generateToken(authentication);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setId(user.getId());
            loginResponse.setFirstName(user.getFirstName());
            loginResponse.setLastName(user.getLastName());
            loginResponse.setGender(user.getGender());
            loginResponse.setMobileNumber(user.getMobileNumber());
            loginResponse.setCorporateId(user.getCompanyId());
            loginResponse.setZipCode(user.getZipCode());
            loginResponse.setDateOfBirth(user.getDateOfBirth());
            loginResponse.setSecret((String) Optional.ofNullable(corporateCompanyRepository.getCorporateSecretKey(user.getCompanyId())).orElse(null));
            if (user.getProfilePicture() != null) {
                loginResponse.setProfilePicture(fileBaseUrl + user.getProfilePicture());
            }

            if (user.getIsLoggedIn() != null) {
                if (!user.getIsLoggedIn()) {
                    corporateCompanyRepository.updateIsLoggedIn(user.getId());
                }
            } else {
                corporateCompanyRepository.updateIsLoggedIn(user.getId());
            }

            return new ResponseEntity<>(new EntityResponse(loginResponse, 0), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.OK);

        }
    }

    @PostMapping("/sendOtpForLoginWithMobileNumber")
    public ResponseEntity<?> sendOtpForLoginWithMobileNumber(@RequestParam String mobileNumber) {
        try {
            boolean exist = iEmployeeAccessService.isMobileNumber(mobileNumber);
            if (exist) {
                iEmployeeAccessService.loginWithMobileNumber(mobileNumber);
                //return new ResponseEntity<>(new CustomResponseMessage("OTP send on registered mobile number", 0), HttpStatus.OK);
                return new ResponseEntity<>(new CustomResponseMessage("OTP send on registered mobile number", 0), HttpStatus.OK);

            } else
                return new ResponseEntity<>(new CustomResponseMessage("Mobile number does not exist", -1), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new CustomResponseMessage(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @PostMapping("/authenticateUserWithOtp")//authenticate User With OtpLogin
    public ResponseEntity<?> authenticateUserWithOtp(@ModelAttribute MobileAuthenticationRequest request) {
        try {
            Boolean check = iEmployeeAccessService.verifyMobileLoginOtp(request.getMobileNumber(), request.getMobileOtp());
            if (check != true) {
                throw new Exception("Please Enter Valid OTP");
            }
             AnvrEmployee user = iEmployeeAccessService.findByEmail(request.getMobileNumber());
            final UserDetails userDetails = iEmployeeAccessService.loadUserByUsername(request.getMobileNumber());
            if (!user.getIsActive() || user.getIsDeleted()) {
                throw new Exception("User Not Active ");
            }
            final String token = jwtTokenUtil.generateTokenForMobileNumberUser(userDetails, user);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setId(user.getId());

            loginResponse.setFirstName(user.getFirstName());
            loginResponse.setLastName(user.getLastName());
            loginResponse.setGender(user.getGender());
            loginResponse.setMobileNumber(user.getMobileNumber());
            loginResponse.setCorporateId(user.getCompanyId());
            loginResponse.setZipCode(user.getZipCode());
            loginResponse.setDateOfBirth(user.getDateOfBirth());
            loginResponse.setSecret(Optional.ofNullable(corporateCompanyRepository.getCorporateSecretKey(user.getCompanyId())).orElse(null));
            if (user.getProfilePicture() != null) {
                loginResponse.setProfilePicture(fileBaseUrl + user.getProfilePicture());
            }

            return new ResponseEntity<>(new EntityResponse(loginResponse, 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponseMessage(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Please Check Username and Password", e);
        }
    }

    @PostMapping("/logoutFcmToken")
    public ResponseEntity<?> logoutFcmToken(@RequestParam Long employeeId) throws Exception {
        try {
            return new ResponseEntity<>(new EntityResponse(iEmployeeAccessService.logoutFcmToken(employeeId), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponseMessage(e.getMessage(), -1), HttpStatus.OK);
        }
    }


    private AnvrEmployee getUserFromToken() {
//         try {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        return iEmployeeAccessService.findByEmail(username);
//        } catch (Exception e) {
//            return iDoctorAccessService.findByEmail("admin@tattvan.com");
//        }
    }
}
