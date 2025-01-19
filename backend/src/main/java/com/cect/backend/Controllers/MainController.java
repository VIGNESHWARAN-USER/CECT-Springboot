package com.cect.backend.Controllers;

import com.cect.backend.Services.MainServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class MainController {

    @Autowired
    MainServices service;

    @RequestMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody Map<String,String> req)
    {
        String email = req.get("Email");
        String pass = req.get("pass");
        System.out.println(email+pass);

        return service.getLoginInfo(email ,pass);
    }

    @RequestMapping("/send_recovery_email")
    public ResponseEntity<?> sendOtpEmail(@RequestBody Map<String, String> req)
    {
        String otp = req.get("OTP");
        String email = req.get("recipient_email");
        return service.sendOtpEmail(email, otp);
    }

    @RequestMapping("/reset-password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> req)
    {
        String newPassword = req.get("newPassword");
        String Email = req.get("Email");
        return service.updatePassword(Email, newPassword);
    }
}
