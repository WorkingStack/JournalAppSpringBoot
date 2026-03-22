package com.test.restProject.services;

import com.test.restProject.apiResponse.WeatherResponse;
import com.test.restProject.appCache.AppCache;
import com.test.restProject.constant.Placeholder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
   @Value("${weather-api-key}")
   private String weatherApiKey;
   private final AppCache appCache;

   private RestTemplate restTemplate;
   private RedisService redisService;
   @Autowired
   public WeatherService(RestTemplate restTemplate, AppCache appCache, RedisService redisService) {
      this.restTemplate = restTemplate;
      this.appCache = appCache;
      this.redisService = redisService;
   }

   public ResponseEntity<WeatherResponse> getWeather(String city) {
      WeatherResponse weather = redisService.getWeatherKey("weather_of_" + city, WeatherResponse.class);
      if(weather != null) {
         System.out.println("data present in redis cloud");
         return new ResponseEntity<>(weather, HttpStatus.OK);
      }
      else {
         String finalAPI = appCache.APP_CACHE.get(AppCache.ApiKeys.WEATHER_API.name()).replace(Placeholder.API_KEY, weatherApiKey).replace(Placeholder.CITY, city);
         ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
         if(response != null) {
            System.out.println("data not present in redis cloud");
            redisService.setWeatherKey("weather_of_" + city, response.getBody(), 300L);
         }
         return response;
      }
   }
}
