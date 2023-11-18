package ru.katok.tamctf.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.time.LocalDateTime;

@Slf4j
@Configuration
public class ApplicationConfig {
    @Bean
    public JedisPool jedisPool(@Value("${REDIS_PASSWORD}") String password, @Value("${REDIS_HOST}") String host) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(1000);
        final JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, password);
        log.info("REDIS INIT DONE ON: {}:{}", host, Protocol.DEFAULT_PORT);
        return jedisPool;
    }

    @Bean
    public PlatformConfig platformConfig(){
//        LocalDateTime now = LocalDateTime.now();
//        return new PlatformConfig("TAM-CTF", "CTF", now, now, now);
        return new PlatformConfig();
    }

}
