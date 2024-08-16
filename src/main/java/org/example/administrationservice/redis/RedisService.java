package org.example.administrationservice.redis;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setValue(String key, String value, Long time) {
        redisTemplate.opsForValue().set(key, value, Duration.of(time, ChronoUnit.SECONDS));
    }

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
