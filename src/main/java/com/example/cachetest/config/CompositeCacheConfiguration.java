package com.example.cachetest.config;

import com.example.cachetest.condition.CompositeCacheCondition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * description: CompositeCacheConfiguration <br>
 * date: 2020/11/19 21:26 <br>
 * author: Neal <br>
 * version: 1.0 <br>
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(CompositeCacheManager.class)
@AutoConfigureAfter(CacheAutoConfiguration.class)
@Conditional({ CompositeCacheCondition.class })
public class CompositeCacheConfiguration {

    @Bean
    @Primary
    public CompositeCacheManager compositeCacheManager(CacheManager[] cacheManagers) {
        return new CompositeCacheManager(cacheManagers);
    }

    @Bean
    public TestCacheProperties testCacheProperties() {
        return new TestCacheProperties();
    }
}
