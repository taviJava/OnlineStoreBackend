package com.project.demo.service;
import com.project.demo.persitance.dto.UserDto;
import com.project.demo.persitance.model.UserModel;
import com.project.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


@Component
public class SendEmailService  {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserRepository userRepository;


   public void sendEmail(String email ,long id, String token, String fullname) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("FashionApp password reset");
        msg.setText("Hello "+fullname+ ", \n \n A request has been received to change the password for your FashionApp account." +
                "\n Please use the link bellow to reset it:"+
                "\n \n "+ "http://localhost:4200/update-password/"+token+"/"+id +
                "\n \nIf you did not initiate this request, please contact us immediately at tavi.zorila@gmail.com.\n \n"+
                "Thank You,\n" +
                "FashionApp Team");

        mailSender.send(msg);

    }


}
