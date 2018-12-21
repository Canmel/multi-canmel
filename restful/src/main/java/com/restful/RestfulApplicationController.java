package com.restful;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.jms.ConnectionFactory;

/**
 * <p>
 * 项目入口
 * </p>
 *
 * @author  *
 *   ┏ ┓   ┏ ┓
 *  ┏┛ ┻━━━┛ ┻┓
 *  ┃         ┃
 *  ┃    ━    ┃
 *  ┃  ┳┛  ┗┳ ┃
 *  ┃         ┃
 *  ┃    ┻    ┃
 *  ┃         ┃
 *  ┗━━┓    ┏━┛
 *     ┃    ┃神兽保佑
 *     ┃    ┃代码无BUG！
 *     ┃    ┗━━━━━━━┓
 *     ┃            ┣┓
 *     ┃            ┏┛
 *     ┗┓┓┏━━━━━━┳┓┏┛
 *      ┃┫┫      ┃┫┫
 *      ┗┻┛      ┗┻┛
 * @since 2018-08-12
 */

@EnableAutoConfiguration
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {
        "com.restful.config.mybatisplus",
        "com.restful.config.application",
        "com.restful.config.activeMq",
        "com.restful.web",
        "com.restful.aop",
        "com.restful.config.security",
        "com.restful.config.swagger",
        "com.restful.service",
        "com.restful.exception"})
public class RestfulApplicationController implements CommandLineRunner {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static Logger logger = LoggerFactory.getLogger(RestfulApplicationController.class);

    public static void main(String[] args) {
        SpringApplication.run(RestfulApplicationController.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("Restful应用启动完成");
    }
}
