package com.example.cachetest.config;

import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description: 自定义缓存的外部化配置
 * date: 2020/11/19 21:29 <br>
 * author: Neal <br>
 * version: 1.0 <br>
 */
@ConfigurationProperties(prefix = "test")
public class TestCacheProperties {

    private CacheType type;

    public CacheType getType() {
        return type;
    }

    public void setType(CacheType type) {
        this.type = type;
    }
}
