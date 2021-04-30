package com.my.mybatis.plugin;

import com.my.mybatis.anno.DESDomain;
import com.my.mybatis.anno.DESField;
import com.my.mybatis.handles.DESHandle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.type.Alias;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;


@Intercepts(
        { @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) }
    )
@Alias("ExecutorPlugin")
public class ExecutorPlugin extends AbstractMybatisPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement arg0 = (MappedStatement)invocation.getArgs()[0];
        Object a1 = invocation.getArgs()[1];
        if (a1 instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap arg1 = (MapperMethod.ParamMap)invocation.getArgs()[1];
            String id = arg0.getId();
            String classStr = id.substring(0, id.lastIndexOf("."));
            String methodStr = id.substring(id.lastIndexOf(".") + 1);
            List<Class<?>> methodParamClasses = getClassMethodParam(id);
            if (methodParamClasses == null) {
                List<ParameterMapping> parameterMappings = arg0.getBoundSql(arg1).getParameterMappings();
                List<Class<?>> params = new ArrayList<>();
                if (!CollectionUtils.isEmpty(parameterMappings)) {
                    for (Map.Entry<String, Object> entry : (Set<Map.Entry<String,Object>>)arg1.entrySet()) {
                        for (ParameterMapping parameterMapping: parameterMappings) {
                            if (entry.getKey().equals(parameterMapping.getProperty())){
                                params.add(entry.getValue().getClass());
                                break;
                            }
                        }
                    }
                }
                putClassMethodParam(id, params);
                methodParamClasses = params;
            }
            Class<?> aClass = Class.forName(classStr);
            if (aClass != null) {
                Method method = aClass.getMethod(methodStr, methodParamClasses.toArray(new Class<?>[methodParamClasses.size()]));
                Annotation[] annotations = method.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof DESDomain) {
                        Parameter[] parameters = method.getParameters();
                        if (parameters != null && parameters.length > 0) {
                            for (Parameter parameter : parameters) {
                                DESField annotation1 = parameter.getAnnotation(DESField.class);
                                Param annotation2 = parameter.getAnnotation(Param.class);
                                if (annotation1 != null) {
                                    DESHandle desHandle = getDESHandle(annotation1.value());
                                    try {
                                        Object value = arg1.get(annotation2.value());
                                        if (value instanceof String) {
                                            arg1.put(annotation2.value(), desHandle.encrypt((String) value));
                                        }
                                    } catch (BindingException ignored) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Class<?> parameterObjectClass = a1.getClass();
            DESDomain desDomain = AnnotationUtils.findAnnotation(parameterObjectClass, DESDomain.class);
            if (desDomain != null) {
                encrypt(a1, parameterObjectClass);
            }
        }
        return invocation.proceed();
    }

}
