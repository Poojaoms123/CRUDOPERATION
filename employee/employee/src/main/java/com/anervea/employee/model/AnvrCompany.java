package com.anervea.employee.model;


import com.anervea.employee.utils.CorporateStatus;
import com.anervea.employee.utils.UserType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="anvr_company")
public class AnvrCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "phone")
    private String phone;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CorporateStatus status;

    @Column(name = "secret_key", unique = true)
    private String secretKey;

    @Column(name = "logo")
    private String logo;

    @Column(name ="is_deleted")
    private Boolean isDeleted;

    @Column(name ="is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", updatable = true)
    private LocalDateTime updatedAt;

    @Column(name = "created_user_type")
    @Enumerated(EnumType.STRING)
    private UserType createdUserType=UserType.CORPORATE;

    @Column(name = "updated_user_type")
    @Enumerated(EnumType.STRING)
    private UserType updatedUserType=UserType.CORPORATE;

}

