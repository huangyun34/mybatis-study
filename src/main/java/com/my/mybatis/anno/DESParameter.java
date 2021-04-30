package com.my.mybatis.anno;

import java.lang.annotation.*;

/**
 * @author huangyun
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DESParameter {
    String value() default "AES";
}
