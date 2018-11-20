package com.restful.annotation;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {
    String value() default "";

    String title() default "";
}
