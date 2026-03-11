package com.test.restProject.services;

import com.test.restProject.apiResponse.WeatherResponse;
import com.test.restProject.appCache.AppCache;
import com.test.restProject.constant.Placeholder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
   @Value("${weather-api-key}")
   private String weatherApiKey;
   private final AppCache appCache;

   RestTemplate restTemplate;
   @Autowired
   public WeatherService(RestTemplate restTemplate, AppCache appCache) {
      this.restTemplate = restTemplate;
      this.appCache = appCache;
   }

   public ResponseEntity<WeatherResponse> getWeather(String city) {
      String finalAPI = appCache.APP_CACHE.get(AppCache.ApiKeys.WEATHER_API.name()).replace(Placeholder.apiKey, weatherApiKey).replace(Placeholder.city, city);
      ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
      return response;
   }
}
