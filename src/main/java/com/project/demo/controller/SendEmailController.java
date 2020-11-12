package com.project.demo.controller;

import com.project.demo.persitance.dto.UserDto;
import com.project.demo.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@CrossOrigin
public class SendEmailController {

    @Autowired
    SendEmailService sendEmailService;


//    @PostMapping("/email/{email}")
//    public void sendEmail(@PathVariable(name = "email") String email)  {
//        sendEmailService.sendEmail(email);
//    }
}
