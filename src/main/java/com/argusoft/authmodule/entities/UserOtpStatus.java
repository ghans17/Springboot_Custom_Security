package com.argusoft.authmodule.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "TBL_User_Otp_Status")
public class UserOtpStatus {

    @Id
    @Column(name = "user_identifier", nullable = false)
    private String userIdentifier; // Use email or phone number

    @Column(name = "otp_retry_count", nullable = false)
    private int otpRetryCount;

    @Column(name = "lockout_end_time")
    private LocalDateTime lockoutEndTime;


}
