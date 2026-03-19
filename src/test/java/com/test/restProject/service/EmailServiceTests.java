package com.test.restProject.service;

import com.test.restProject.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {
   @Autowired
   private EmailService emailService;
   @Test
   public void testingEmailService() {
      emailService.sendEmail(
              "jeyipi9597@hlkes.com",
              "Testing mail from workstack",
              "Congratulations, You have selected as Junior Software Engineer in TH System Company"
      );
   }
}
