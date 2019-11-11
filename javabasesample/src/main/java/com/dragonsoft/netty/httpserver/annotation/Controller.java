package com.dragonsoft.netty.httpserver.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2019/11/10.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface Controller {

    public String value() default "";

}
