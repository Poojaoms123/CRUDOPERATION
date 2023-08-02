package com.anervea.employee.model;

import com.anervea.employee.utils.UserType;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "anvr_employees")
public class AnvrEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;//employeeId

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "employee_code")
    private String employeeCode;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_otp")
    private String mobileOtp;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "address_line1",columnDefinition = "TEXT")
    private String addressLine1;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "email_verified_at")
    private String emailVerifiedAt;

    @Column(name = "remember_token")
    private String rememberToken;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "forgot_otp")
    private String forgotOtp;

    @Column(name = "is_plan_allocate")
    private Boolean isPlanAllocate=false;

    @Column(name = "fcm_token", columnDefinition = "TEXT")
    private String fcmToken;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", updatable = true)
    private LocalDateTime updatedAt;

    @Column(name = "is_logged_in")
    private Boolean isLoggedIn;

    @Column(name = "is_active")
    private Boolean isActive=true;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_user_type")
    @Enumerated(EnumType.STRING)
    private UserType createdUserType=UserType.CORPORATE;

    @Column(name = "updated_user_type")
    @Enumerated(EnumType.STRING)
    private UserType updatedUserType=UserType.CORPORATE;

}
