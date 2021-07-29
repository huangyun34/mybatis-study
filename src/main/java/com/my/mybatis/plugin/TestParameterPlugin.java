package com.my.mybatis.plugin;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.Alias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Intercepts(
        {
                @Signature(type = ParameterHandler.class, method = "getParameterObject", args = {}),
                @Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class}),
        }
)
@Alias("TestParameterPlugin")
public class TestParameterPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("======" + invocation.getMethod().toString());
        return invocation.proceed();
    }
}
