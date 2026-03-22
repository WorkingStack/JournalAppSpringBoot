package com.test.restProject.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisService {
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;
   @Autowired
   private ObjectMapper objectMapper;

   public <T> T getWeatherKey(String key, Class<T> entityClass) {
      try {
//         first check that key present or not if not then it will throw exception and return null
         Object o = redisTemplate.opsForValue().get(key);
         if(o == null || o.toString().isEmpty()) {
            return null;
         }
         System.out.println(key);
//         ObjectMapper objectMapper = new ObjectMapper();
         return objectMapper.readValue(o.toString(), entityClass);
      }
      catch (Exception e) {
         log.error("Exception occurred --- ", e);
         return null;
      }
   }

   public void setWeatherKey(String key, Object o, Long ttl) {
      try {
//         ObjectMapper objectMapper = new ObjectMapper();
         String jsonString = objectMapper.writeValueAsString(o);
         redisTemplate.opsForValue().set(key, jsonString, ttl, TimeUnit.SECONDS);
      }
      catch (Exception e) {
         log.error("Exception occurred --- ", e);
      }
   }
}

/*

| Place     | Value                     |
| --------- | ------------------------- |
| Java code | `"{\"name\":\"aditya\"}"` |
| Redis     | `{"name":"aditya"}`       |


 */