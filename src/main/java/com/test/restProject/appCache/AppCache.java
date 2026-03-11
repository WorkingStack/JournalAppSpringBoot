package com.test.restProject.appCache;

import com.test.restProject.entity.ConfigJournalAppEntity;
import com.test.restProject.repository.ConfigJournalApplication;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
   public enum ApiKeys {
      WEATHER_API;
   }
   private final ConfigJournalApplication configJournalApplication;
   @Autowired
   public AppCache(ConfigJournalApplication configJournalApplication) {
      this.configJournalApplication = configJournalApplication;
   }

   public Map<String, String> APP_CACHE;

   @PostConstruct
   public void init() {
      APP_CACHE = new HashMap<>();
      List<ConfigJournalAppEntity> all = configJournalApplication.findAll();
      for (ConfigJournalAppEntity configApp: all) {
         APP_CACHE.put(configApp.getKey(), configApp.getValue());
      }
   }
}
