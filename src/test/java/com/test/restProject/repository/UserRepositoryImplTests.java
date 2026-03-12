package com.test.restProject.repository;

import com.test.restProject.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserRepositoryImplTests {

   @Autowired
   private UserRepositoryImpl userRepository;
   @Test
   void testGetAllUserFromUserRepositoryImpl() {
      List<User> users = userRepository.getAllUserInCustomizeWay();
      Assertions.assertNotNull(users);
   }
}
