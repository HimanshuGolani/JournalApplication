package com.Himanshu.JournalApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

	// A method that enables your application to use Transactions (sessions) to achieve atomicity and isolation.
	//Platform Transactional Manager
	@Bean
	public PlatformTransactionManager sessionManager(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}



}
