package com.test.restProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

   @Bean
   public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {

      RedisTemplate<String, Object> template = new RedisTemplate<>();
      template.setConnectionFactory(connectionFactory);

//      create one object to pass for key and value if you direct write new StringRedisSerializer() then it will
//      create two different object of StringSerializer
      StringRedisSerializer stringSerializer = new StringRedisSerializer();

      // Key & Value
      template.setKeySerializer(stringSerializer);
      template.setValueSerializer(stringSerializer);

//      this is extra code but it is also good
      // Hash Key & Value (IMPORTANT)
//      template.setHashKeySerializer(stringSerializer);
//      template.setHashValueSerializer(stringSerializer);
//
//      template.afterPropertiesSet();

      return template;
   }
}
