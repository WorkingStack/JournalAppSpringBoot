package com.test.restProject.service;

import com.test.restProject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
   @Autowired
   private UserRepository userRepository;

   @Disabled
   @Test //  Test 1
   public void testFindByUsername() {
      // this is only one test even though you have written many statements still
      // it takes as one test
//      Assertions.assertEquals(4, 2 + 2);
      Assertions.assertNotNull(userRepository.findByUserName("admin"));
   }

   @Disabled
   @Test // Test 2
   public void testArithmeticEquation() {
      Assertions.assertEquals(23, 26 - 3);
   }

   @Disabled
   @Test // Test 3
   public void testMyName() {
      Assertions.assertEquals("aditya", "aditya");
   }

   @Disabled
   @ParameterizedTest // this is 3 tests
   @CsvSource({
      "1, 1, 2", "2, 2, 4", "3, 3, 9"
   })
   public void testNumbers(int a, int b, int expected) {
      Assertions.assertEquals(expected, a + b);
   }

   @Disabled
   @ParameterizedTest // this is 3 tests
   @CsvFileSource(resources = "/sample.csv", numLinesToSkip = 1) // number of line 1 skip, not first
   public void testUsername(String username) {
      Assertions.assertNotNull(userRepository.findByUserName(username));
   }

   @Disabled
   @ParameterizedTest
   @ValueSource(strings = { // suppose you want to give number then use ints, floats, doubles
           "ram",
           "aditya",
           "admin"
   })
   public void testUsername2(String user) {
      Assertions.assertNotNull(userRepository.findByUserName(user));
   }

//   total number of tests are : 6, so passed tests are 5 and failed test one
}
