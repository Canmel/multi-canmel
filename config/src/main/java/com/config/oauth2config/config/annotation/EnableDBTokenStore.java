package com.config.oauth2config.config.annotation;

import com.config.oauth2config.config.store.DBTokenStore;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 在启动类上添加该注解来----开启通过数据库存储令牌
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DBTokenStore.class)
public @interface EnableDBTokenStore {
}
