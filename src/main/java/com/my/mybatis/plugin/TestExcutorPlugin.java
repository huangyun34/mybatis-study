package com.my.mybatis.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.Alias;

@Intercepts(
        {
                @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
                @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),
                @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
                @Signature(type = Executor.class, method = "queryCursor", args = { MappedStatement.class, Object.class, RowBounds.class }),
                @Signature(type = Executor.class, method = "flushStatements", args = {}),
                @Signature(type = Executor.class, method = "commit", args = {boolean.class}),
                @Signature(type = Executor.class, method = "rollback", args = {boolean.class}),
                @Signature(type = Executor.class, method = "createCacheKey", args = { MappedStatement.class, Object.class, RowBounds.class, BoundSql.class }),
                @Signature(type = Executor.class, method = "isCached", args = { MappedStatement.class, CacheKey.class }),
                @Signature(type = Executor.class, method = "clearLocalCache", args = {}),
                @Signature(type = Executor.class, method = "deferLoad", args = {MappedStatement.class, MetaObject.class, String.class, CacheKey.class, Class.class}),
                @Signature(type = Executor.class, method = "getTransaction", args = {}),
                @Signature(type = Executor.class, method = "close", args = {boolean.class}),
                @Signature(type = Executor.class, method = "setExecutorWrapper", args = {Executor.class}),
                @Signature(type = Executor.class, method = "clearLocalCache", args = {}),
                @Signature(type = Executor.class, method = "clearLocalCache", args = {}),
        }
)
@Alias("TestExecutorPlugin")
public class TestExcutorPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("======" + invocation.getMethod().toString());
        return invocation.proceed();
    }
}
