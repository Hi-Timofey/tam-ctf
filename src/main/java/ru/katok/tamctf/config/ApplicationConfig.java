package ru.katok.tamctf.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ApplicationConfig {
//    @Bean
//    public JedisPool jedisPool(@Value("${REDIS_PASSWORD}") String password, @Value("${REDIS_HOST}") String host) {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        final JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, password);
//        log.info("REDIS INIT DONE ON: {}:{}", host, Protocol.DEFAULT_PORT);
//        return jedisPool;
//    }
}
