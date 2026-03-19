package com.test.restProject.scheduler;

import com.test.restProject.entity.JournalEntry;
import com.test.restProject.entity.User;
import com.test.restProject.enums.Sentiments;
import com.test.restProject.repository.UserRepository;
import com.test.restProject.repository.UserRepositoryImpl;
import com.test.restProject.services.EmailService;
import com.test.restProject.services.SentimentsAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
   @Autowired
   private EmailService emailService;
   @Autowired
   private UserRepositoryImpl userRepository;
   @Autowired
   private SentimentsAnalysisService sentimentsAnalysisService;

   @Scheduled(cron = "0 0 17 ? * WED")
   public void fetchUserAndSendEmail() {
      List<User> allUserInCustomizeWay = userRepository.getAllUserInCustomizeWay();
      for (User user: allUserInCustomizeWay) {
         List<Sentiments> collectSentiments = user.getJournalEntryList().stream().map(x -> x.getSentiments()).collect(Collectors.toList());
         Map<Sentiments, Integer> countSentiments = new HashMap<>();
         for (Sentiments sentiment : collectSentiments) {
            if(sentiment != null) {
               countSentiments.put(sentiment, countSentiments.getOrDefault(sentiment, 0) + 1);
            }
         }
         Sentiments mostFrequentSentiments = null;
         int maxCount = 0;
         for(Map.Entry<Sentiments, Integer> entry: countSentiments.entrySet()) {
            if(entry.getValue() > maxCount) {
               maxCount = entry.getValue();
               mostFrequentSentiments = entry.getKey();
            }
         }

         if(mostFrequentSentiments != null) {
            emailService.sendEmail(user.getEmail(), "Most frequent sentiment", "Hey, " + user.getUserName() + "Your most frequent sentiment is - " + mostFrequentSentiments);
         }
      }
   }
}
