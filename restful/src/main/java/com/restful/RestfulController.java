package com.restful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestfulController implements CommandLineRunner {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    Logger logger = LoggerFactory.getLogger(RestfulController.class);

    public static void main(String[] args) {
        SpringApplication.run(RestfulController.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("Restful应用启动完成");
    }
}
