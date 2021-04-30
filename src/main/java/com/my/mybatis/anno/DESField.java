package com.my.mybatis.anno;

import java.lang.annotation.*;

/**
 * @author huangyun
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DESField {
    String value() default "AES";
}
