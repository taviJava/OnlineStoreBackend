package com.project.demo.controller;

import com.project.demo.persitance.dto.UserDto;
import com.project.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class PasswordTokenController {
    @Autowired
    private UserService userService;

}
