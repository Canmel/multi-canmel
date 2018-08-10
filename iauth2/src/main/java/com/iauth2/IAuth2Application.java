package com.iauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IAuth2Application implements CommandLineRunner{

    private Logger logger = LoggerFactory.getLogger(IAuth2Application.class);

    public static void main(String[] args) {
        SpringApplication.run(IAuth2Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("IWEB2项目启动完成");
    }
}
