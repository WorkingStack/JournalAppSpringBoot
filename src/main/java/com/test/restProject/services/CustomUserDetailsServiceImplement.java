package com.test.restProject.services;

import com.test.restProject.entity.User;
import com.test.restProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

//this is specially for spring security and extending class UserDetailsService
@Component
public class CustomUserDetailsServiceImplement implements UserDetailsService {
   private final UserRepository userRepository;

   @Autowired // this constructor autowired by instruction of sonar Qube
   public CustomUserDetailsServiceImplement(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findByUserName(username);
      if(user != null) {
         return org.springframework.security.core.userdetails.User.builder()
                 .username(user.getUserName()).password(user.getUserPassword())
                 .roles(user.getRoles().toArray(new String[0]))
                 .build();
      }
      throw new UsernameNotFoundException("Username not found");
   }
}
