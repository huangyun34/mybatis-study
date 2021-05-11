package com.my.mybatis.plugin;

import com.my.mybatis.anno.DESDomain;
import com.my.mybatis.anno.DESField;
import com.my.mybatis.domain.AdminUser;
import com.my.mybatis.handles.AESHandle;
import com.my.mybatis.handles.DESHandle;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.type.Alias;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;


@Intercepts(
        { @Signature(type = ParameterHandler.class, method = "setParameters", args = { PreparedStatement.class }) }
    )
@Alias("ParamPlugin")
public class ParamPlugin extends AbstractMybatisPlugin implements Interceptor {

    private static final List<DESHandle> HANDLES = new ArrayList<>();

    static {
        HANDLES.add(new AESHandle());
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
        Field paramsFiled = parameterHandler.getClass().getDeclaredField("parameterObject");
        paramsFiled.setAccessible(true);

        Object parameterObject = paramsFiled.get(parameterHandler);
        if (parameterObject != null) {
            if (parameterObject instanceof MapperMethod.ParamMap) {

//                (DefaultParameterHandler)parameterHandler.
//                ReflectorFactory reflectorFactory = ((DefaultParameterHandler) parameterHandler).boundSql.metaParameters.reflectorFactory;
//                MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) parameterObject;
//                Object mobile = paramMap.get("mobile");
//                System.out.println(mobile);
            } else {
                Class<?> parameterObjectClass = parameterObject.getClass();
                DESDomain desDomain = AnnotationUtils.findAnnotation(parameterObjectClass, DESDomain.class);
                if (desDomain != null) {
                    encrypt(parameterObject, parameterObjectClass);
                }
            }
        }
        return invocation.proceed();
    }
}
