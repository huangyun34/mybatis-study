package com.my.mybatis.utils;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.plugin.Plugin;

import java.lang.reflect.Field;

/**
 * @author huangyun
 */
public class MybatisProxyUtils {
    public static RoutingStatementHandler getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        Plugin aopProxy = (Plugin) h.get(proxy);
        Field target = aopProxy.getClass().getDeclaredField("target");
        target.setAccessible(true);
        return ((RoutingStatementHandler)target.get(aopProxy));
    }
}
