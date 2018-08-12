package com.restful;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

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
@EnableTransactionManagement
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.restful.config.mybatisplus",
        "com.restful.web",
        "com.restful.service"})
public class RestfulController implements CommandLineRunner {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static Logger logger = LoggerFactory.getLogger(RestfulController.class);

    public static void main(String[] args) {
//        SpringApplication app = new SpringApplication(Application.class);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);
//        logger.info("PortalApplication is success!");
//        System.err.println("sample started. http://localhost:8080/user/test");
        SpringApplication.run(RestfulController.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("Restful应用启动完成");
    }
}
