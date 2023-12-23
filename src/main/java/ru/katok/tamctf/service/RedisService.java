package ru.katok.tamctf.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.service.interfaces.IRedisService;

import java.util.List;

@Service
@AllArgsConstructor
public class RedisService implements IRedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void setBan(String ip, boolean banned) {
        List<String> bannedIps = redisTemplate.opsForList().range("banned", 0, -1);
        if (!bannedIps.contains(ip)) {
            if (banned)
                redisTemplate.opsForList().rightPush("banned", ip);
            else
                redisTemplate.opsForList().remove("banned", 0, ip);
        }
    }

    public boolean isBanned(String ip) {
        List<String> bannedIps = redisTemplate.opsForList().range("banned", 0, -1);
        return bannedIps.contains(ip);
    }

    public List<String> getAllBannedIps() {
        return redisTemplate.opsForList().range("banned", 0, -1);
    }
}
