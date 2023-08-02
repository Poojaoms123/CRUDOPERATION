package com.anervea.company.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
   @Getter
    @Setter
    @Entity
    @Table(name="anvr_company_accesses")
    public class AnvrCompanyAcess {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "company_access_id")
        private  Long companyAccessId;

        @Column(name = "company_id")
        private  Long companyId;

        @Column(name = "username", unique = true)
        private String userName;

        @Column(name = "password")
        private String password;

        @Column(name = "last_password_changed_date")
        private Date lastPasswordChangeDate;

        @Column(name = "password_changed_by")
        private Long passwordChangedBy;

        @Column(name = "is_2mfa_enable")
        private Boolean is2MfaEnable;

        @Column(name = "factor2_email")
        private String factor2Email;

        @Column(name = "factor2_mobile_number")
        private String factor2MobileNumber;

        @Column(name = "is_login_enable")
        private Boolean isLoginEnable;

        @Column(name = "createdby")
        private Long createdBy;

        @Column(name = "updated_by")
        private Long updatedBy;

        @CreationTimestamp
        @Column(updatable = false, name = "created_at")
        private Timestamp createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @Column(name = "is_active")
        private Boolean isActive;


    }


