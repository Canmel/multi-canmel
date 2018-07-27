package com.config.oauth2config.config.annotation;

import com.config.oauth2config.config.service.DBClientDetailsService;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 在启动类上添加该注解来----开启从数据库加载客户端详情
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DBClientDetailsService.class)
public @interface EnableDBClientDetailsService {
}
