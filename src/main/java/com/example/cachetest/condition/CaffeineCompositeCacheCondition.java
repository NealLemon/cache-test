package com.example.cachetest.condition;

import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.ClassMetadata;

/**
 * description: CompositeCacheCondition <br>
 * date: 2020/11/19 21:29 <br>
 * author: Neal <br>
 * version: 1.0 <br>
 */
public class CaffeineCompositeCacheCondition extends SpringBootCondition {
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String sourceClass = "";
        if (metadata instanceof ClassMetadata) {
            sourceClass = ((ClassMetadata) metadata).getClassName();
        }
        ConditionMessage.Builder message = ConditionMessage.forCondition("Cache", sourceClass);
        Environment environment = context.getEnvironment();
        try {
            //获取spring cache 原生的缓存类型
            BindResult<CacheType> specified = Binder.get(environment).bind("spring.cache.type", CacheType.class);
            //获取自定义的缓存类型
            BindResult<CacheType> testCache = Binder.get(environment).bind("test.type", CacheType.class);
            //如果一致 则执行自动化配置，使用spring cache原生的缓存类型
            if (specified.get() == testCache.get()) {
                return ConditionOutcome.noMatch(message.because("cache type is same"));
            }
            //如果不一致，则执行自动化配置 初始化CompositeCacheManager
            if(CacheType.CAFFEINE == testCache.get())
            return ConditionOutcome.match(message.because("cache type is redis"));
        }
        catch (BindException ex) {
        }
        return ConditionOutcome.noMatch(message.because("unknown cache type"));
    }
}
