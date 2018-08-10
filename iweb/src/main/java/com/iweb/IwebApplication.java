package com.iweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//@EnableResJWTTokenStore
public class IwebApplication implements CommandLineRunner {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    Logger logger = LoggerFactory.getLogger(IwebApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(IwebApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("IWeb应用启动完成");
    }
}
