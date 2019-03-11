package top.leaftogo.tanmu.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void setString(String key,String value,long timeout){
        redisTemplate.opsForValue().set(key,value,timeout, TimeUnit.SECONDS);
        return ;
    }

    public String getString(String key){
        String value = redisTemplate.opsForValue().get(key);
        if(value == null) return null;
        return value;
    }

    public boolean remove(String key){
        return redisTemplate.delete(key);
    }




}
