package com.test.restProject.services;

import com.test.restProject.entity.JournalEntry;
import com.test.restProject.entity.User;
import com.test.restProject.repository.JournalEntryRepository;
import com.test.restProject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
   @Autowired
   private UserRepository userRepository;
   @Autowired // static + @Autowired does not work
   private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//   for logging
//   private static final Logger logger = LoggerFactory.getLogger(UserService.class);

//   public void saveEntry(User user) {
//      userRepository.save(user); // this pre-defined method by MongoRepository
//   }

//   here we have created another method for user creation cause the problem while registering user
//   we are stored username as plain text and password as hash code but when I update journal entries
//   or if we are posting data on journal url that same saveEntry method was calling and encoder again
//   change password like same password + salt added into it that's why for registering and updating
//   we have created separate method to update data
   public void createUser(User user) {
      try {
         user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
         user.setRoles(Arrays.asList("USER"));
         userRepository.save(user);
      }
      catch (Exception e) {
         log.error("heyyyyyyyyy error occurred for USER: '{}'", user.getUserName()); // default enabled
         log.info("heyyyyyyyyy exception information"); // default enabled
         log.warn("heyyyyyyyyy warning in exception"); // default enabled
         log.debug("heyyyyyyyyy debugging in exception"); // default disabled
         log.trace("heyyyyyyyyy tracing in exception"); // default disabled

         throw new RuntimeException(e.getMessage());
      }
   }

   public void saveEntry(User user) {
      userRepository.save(user);
   }

   public void saveAdmin(User user) {
      user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
      user.setRoles(Arrays.asList("USER", "ADMIN"));
      userRepository.save(user);
   }
   public List<User> getAllData() {
      return userRepository.findAll();
   }

   public Optional<User> getDataById(ObjectId id) {
      return userRepository.findById(id);
   }

   public void deleteData(ObjectId id) {
      userRepository.deleteById(id);
   }

   public User findByUsername(String username) {
      return userRepository.findByUserName(username);
   }
}
