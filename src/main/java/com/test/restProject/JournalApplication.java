package com.test.restProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

//	this is required if you are using transactional annotations
	@Bean
	public PlatformTransactionManager performEntire(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

//@Bean = put something into the warehouse
//@Autowired = take something out of the warehouse
