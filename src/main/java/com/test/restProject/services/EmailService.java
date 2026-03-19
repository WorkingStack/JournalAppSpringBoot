package com.test.restProject.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
   private JavaMailSender javaMailSender;
   @Autowired
   public EmailService(JavaMailSender javaMailSender) {
      this.javaMailSender = javaMailSender;
   }

   public void sendEmail(String to, String subject, String message) {
      try {
         SimpleMailMessage mail = new SimpleMailMessage();
         mail.setTo(to);
         mail.setSubject(subject);
         mail.setText(message);
         javaMailSender.send(mail);
      }
      catch (Exception e) {
         log.error("ERROR GOT ME THIS: {}", e.getMessage());
      }
   }
}
