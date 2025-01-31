package stockserver.redis;


import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> template;

    // ValueOperations를 통해 간단한 CRUD 기능 구현
    public void insert(String key, String value) {
        template.opsForValue().set(key, value);
    }

    public String read(String key) {
        return template.opsForValue().get(key);
    }

    public void update(String key, String newValue) {
        template.opsForValue().set(key, newValue);
    }

    public void delete(String key) {
        template.delete(key);
    }
}