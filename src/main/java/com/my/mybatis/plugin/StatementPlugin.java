package com.my.mybatis.plugin;

import com.my.mybatis.anno.DESDomain;
import com.my.mybatis.anno.DESField;
import com.my.mybatis.domain.AdminUser;
import com.my.mybatis.handles.AESHandle;
import com.my.mybatis.handles.DESHandle;
import com.my.mybatis.utils.MybatisProxyUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.type.Alias;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;


@Intercepts(
        {
//                @Signature(type = StatementHandler.class, method = "parameterize", args = { Statement.class }),
//                @Signature(type = StatementHandler.class, method = "getBoundSql", args = {}),
                //在sql执行后，修改想要处理的值
                @Signature(type = StatementHandler.class, method = "update", args = {Statement.class})
        }
    )
@Alias("StatementPlugin")
public class StatementPlugin extends AbstractMybatisPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //有些情况会拿到动态代理对象，需要从中获取代理的对象
//        RoutingStatementHandler jdkDynamicProxyTargetObject = MybatisProxyUtils.getJdkDynamicProxyTargetObject(invocation.getTarget());
        RoutingStatementHandler routingStatementHandler = (RoutingStatementHandler) invocation.getTarget();
        Object parameterObject = routingStatementHandler.getBoundSql().getParameterObject();
        if (null != parameterObject) {
            if (parameterObject instanceof MapperMethod.ParamMap) {
                Set<Object> set = new HashSet<>();
                MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) parameterObject;
                for (Object value : paramMap.values()) {
                    if (value == null) {
                        continue;
                    }
                    //防止同一个对象重复处理
                    if (set.contains(value)) {
                        continue;
                    }
                    Class<?> aClass = value.getClass();
                    DESDomain desDomain = AnnotationUtils.findAnnotation(aClass, DESDomain.class);
                    if (desDomain != null) {
                        decrypt(value, aClass);
                    }
                    set.add(value);
                }
            } else {
                Class<?> parameterObjectClass = parameterObject.getClass();
                DESDomain desDomain = AnnotationUtils.findAnnotation(parameterObjectClass, DESDomain.class);
                if (desDomain != null) {
                    decrypt(parameterObject, parameterObjectClass);
                }
            }
        }
        return invocation.proceed();
//        RoutingStatementHandler target = (RoutingStatementHandler)invocation.getTarget();
//        BoundSql boundSql = target.getBoundSql();
//        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//        for (ParameterMapping parameterMapping : parameterMappings) {
//            System.out.println(parameterMapping.getProperty());
//        }
//        return invocation.proceed();
    }

}
