package com.test.restProject.controller;

import com.test.restProject.entity.User;
import com.test.restProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
   @Autowired
   private UserService userService;

   @PostMapping("/create-user")
   public String savedUser(@RequestBody User user) {
      userService.createUser(user);
      return "user saved, OK System!";
   }

}
