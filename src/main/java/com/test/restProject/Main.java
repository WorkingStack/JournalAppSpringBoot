package com.test.restProject;

interface Test {
   default void testPurpose() {
      System.out.println("Hello from test purpose");
   }
}

public class Main implements Test {
   public static void main(String[] args) {
      new Main().testPurpose();
   }
}
