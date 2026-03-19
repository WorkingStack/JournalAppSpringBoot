package com.test.restProject.services;

import org.springframework.stereotype.Service;

@Service
public class SentimentsAnalysisService {
   public String getSentiments(int num) {
      return num == 1 ? "positive vibes" : num == -1 ? "negative vibes" : "neutral vibes";
   }
}
