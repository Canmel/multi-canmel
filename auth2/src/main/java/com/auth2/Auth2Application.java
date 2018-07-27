package com.auth2;

import com.config.oauth2config.config.annotation.EnableAuthJWTTokenStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAuthJWTTokenStore
public class Auth2Application {
    public static void main(String[] args) {
        SpringApplication.run(Auth2Application.class, args);
    }
}
