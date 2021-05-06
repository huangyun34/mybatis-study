package com.my.mybatis.plugin;

import com.my.mybatis.anno.DESDomain;
import com.my.mybatis.anno.DESField;
import com.my.mybatis.domain.AdminUser;
import com.my.mybatis.handles.AESHandle;
import com.my.mybatis.handles.DESHandle;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.type.Alias;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


@Intercepts(
        { @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) }
    )
@Alias("ResultPlugin")
public class ResultPlugin extends AbstractMybatisPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object proceed = invocation.proceed();
        if (null == proceed) {
            return null;
        }
        if (proceed instanceof List) {
            List proceed1 = (List) proceed;
            if (proceed1.size() != 0) {
                Class<?> aClass = ((List) proceed).get(0).getClass();
                if (AnnotationUtils.findAnnotation(aClass, DESDomain.class) != null) {
                    decrypt((ArrayList) proceed, aClass);
                }
            }
        } else {
            Class<?> aClass = proceed.getClass();
            if (AnnotationUtils.findAnnotation(aClass, DESDomain.class) != null) {
                decrypt((ArrayList) proceed, aClass);
            }
        }
        return proceed;
    }

}
