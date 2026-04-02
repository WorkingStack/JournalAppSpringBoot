package com.test.restProject.cron;

import com.test.restProject.scheduler.UserScheduler;
import com.test.restProject.scheduler.UserScheduler1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulerTest {
   @Autowired
   private UserScheduler1 userScheduler1;

   @Test
   public void testKafkaEmailService() {
      userScheduler1.fetchUserAndEmailStoreInKafka();
   }
}
