package com.argusoft.authmodule.Login;

public class LoginRequest {

    private String username;
    private String password;
    private String otpCode;
    // Default constructor
    public LoginRequest() {
    }

    // Parameterized constructor
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter and setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }
}

