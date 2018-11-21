package com.restful.annotation;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SaveLog {
    String value() default "";

    String title() default "";
}
