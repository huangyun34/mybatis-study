package com.my.mybatis.plugin;

import com.my.mybatis.anno.DESDomain;
import com.my.mybatis.anno.DESField;
import com.my.mybatis.anno.DESParameter;
import com.my.mybatis.handles.DESHandle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.Alias;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;


@Intercepts(
        {
                @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
                @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),
                @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
                @Signature(type = Executor.class, method = "queryCursor", args = { MappedStatement.class, Object.class, RowBounds.class }),
        }
    )
@Alias("ExecutorPlugin")
public class ExecutorPlugin extends AbstractMybatisPlugin implements Interceptor {

    /**
     * 不能在这里做对象处理，可能引起对象改变
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement arg0 = (MappedStatement)invocation.getArgs()[0];
        Object a1 = invocation.getArgs()[1];
        if (null != a1) {
            if (a1 instanceof MapperMethod.ParamMap) {
                //要想实现加密，需要参数需要传地对象，对象上需要有@DESDomain，加密字段需要有@DESField
                MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) a1;
                Set<Object> set = new HashSet<>();
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
                        encrypt(value, aClass);
                    }
                    set.add(value);
                }

                //暂时无法解决param传参数问题，注视掉
//            MapperMethod.ParamMap arg1 = (MapperMethod.ParamMap)a1;
//            String id = arg0.getId();
//            String classStr = id.substring(0, id.lastIndexOf("."));
//            String methodStr = id.substring(id.lastIndexOf(".") + 1);
//            SpecialMethodInfo specialMethodInfo = getClassMethodParam(id);
//            List<Class<?>> classes;
//            boolean cache = false;
//            if (specialMethodInfo == null) {
//                cache = true;
//                List<ParameterMapping> parameterMappings = arg0.getBoundSql(arg1).getParameterMappings();
//                List<Class<?>> params = new ArrayList<>();
//                if (!CollectionUtils.isEmpty(parameterMappings)) {
//                    for (Map.Entry<String, Object> entry : (Set<Map.Entry<String,Object>>)arg1.entrySet()) {
//                        for (ParameterMapping parameterMapping: parameterMappings) {
//                            if (entry.getKey().equals(parameterMapping.getProperty())){
//                                params.add(entry.getValue().getClass());
//                                break;
//                            }
//                        }
//                    }
//                }
//                classes = params;
//            } else {
//                classes = specialMethodInfo.classes;
//            }
//            //反射找到有注解的字段并处理,返回是否匹配特殊富豪
//            boolean match = handle(classStr, methodStr, classes, arg1);
//            //信息加入缓存
//            if (cache) {
//                putClassMethodParam(id, new SpecialMethodInfo(match, match ? classes : null));
//            }
            } else {
                Class<?> parameterObjectClass = a1.getClass();
                DESDomain desDomain = AnnotationUtils.findAnnotation(parameterObjectClass, DESDomain.class);
                if (desDomain != null) {
                    encrypt(a1, parameterObjectClass);
                }
            }
        }
        return invocation.proceed();
    }

    private boolean handle(String classStr, String methodStr, List<Class<?>> classes, MapperMethod.ParamMap arg1) throws Throwable {
        boolean match = false;
        Class<?> aClass = Class.forName(classStr);
        if (aClass != null && classes != null) {
            Method method = aClass.getMethod(methodStr, classes.toArray(new Class<?>[classes.size()]));
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof DESDomain) {
                    Parameter[] parameters = method.getParameters();
                    if (parameters != null && parameters.length > 0) {
                        for (Parameter parameter : parameters) {
                            DESParameter annotation1 = parameter.getAnnotation(DESParameter.class);
                            Param annotation2 = parameter.getAnnotation(Param.class);
                            if (annotation1 != null) {
                                match = true;
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
        return match;
    }

}
