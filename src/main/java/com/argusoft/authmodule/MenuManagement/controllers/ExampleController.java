package com.argusoft.authmodule.MenuManagement.controllers;


import com.argusoft.authmodule.custom.ValidateAccess;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExampleController {

    @ValidateAccess(appId = "MY_APP")
    @GetMapping("/secure-endpoint")
    public String secureEndpoint() {
        return "Secure Endpoint Accessed!";
    }
}