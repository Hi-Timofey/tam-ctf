package ru.katok.tamctf.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ru.katok.tamctf.service.interfaces.IRedisService;

@Service
@AllArgsConstructor
public class RedisService implements IRedisService {

    private final JedisPool jedisPool;

    public void initStateDefaults() {
        try (Jedis jedis = jedisPool.getResource()) {
            // Setting defaults
            jedis.setnx("state/gameStarted", "false");
            jedis.setnx("state/gameEnded", "false");
            jedis.setnx("state/freeze", "false");
        }
    }


//    public boolean isFreeze() {
//        try (Jedis jedis = jedisPool.getResource()) {
//            return jedis.get("state/freeze").equals("true");
//        }
//    }
//
//    public void setFreeze(boolean value) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.set("state/freeze", Boolean.toString(value));
//        }
//    }
//
//    public boolean isGameStarted() {
//        try (Jedis jedis = jedisPool.getResource()) {
//            return jedis.get("state/gameStarted").equals("true");
//        }
//    }
//
//    public void setGameStarted(boolean value) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.set("state/gameStarted", Boolean.toString(value));
//        }
//    }
//
//    public boolean isGameEnded() {
//        try (Jedis jedis = jedisPool.getResource()) {
//            return jedis.get("state/gameEnded").equals("true");
//        }
//    }
//
//    public void setGameEnded(boolean value) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.set("state/gameEnded", Boolean.toString(value));
//        }
//
//    }
}
