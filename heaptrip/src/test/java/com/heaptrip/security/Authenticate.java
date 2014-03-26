package com.heaptrip.security;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Authenticate {

    String userid() default "";

    String username() default "";

    String[] roles() default {};
}