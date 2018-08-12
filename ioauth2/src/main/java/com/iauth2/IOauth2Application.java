package com.iauth2;

import com.config.oauth2config.config.annotation.EnableAuthJWTTokenStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAuthJWTTokenStore
public class IOauth2Application implements CommandLineRunner{

    private Logger logger = LoggerFactory.getLogger(IOauth2Application.class);

    public static void main(String[] args) {
        SpringApplication.run(IOauth2Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("IAUTH2项目启动完成");
    }
}
