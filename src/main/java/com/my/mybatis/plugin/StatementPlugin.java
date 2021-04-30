package com.my.mybatis.plugin;

import com.my.mybatis.anno.DESDomain;
import com.my.mybatis.anno.DESField;
import com.my.mybatis.domain.AdminUser;
import com.my.mybatis.handles.AESHandle;
import com.my.mybatis.handles.DESHandle;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Intercepts(
        { @Signature(type = StatementHandler.class, method = "parameterize", args = { Statement.class }) }
    )
@Alias("StatementPlugin")
public class StatementPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
//        RoutingStatementHandler target = (RoutingStatementHandler)invocation.getTarget();
//        BoundSql boundSql = target.getBoundSql();
//        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//        for (ParameterMapping parameterMapping : parameterMappings) {
//            System.out.println(parameterMapping.getProperty());
//        }
        return invocation.proceed();
    }

}
