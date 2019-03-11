package top.leaftogo.tanmu.Config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by chenqixuan on 17/10/25.
 * 集成RedisTemplate
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {

    //获取springboot配置文件的值 (get的时候获取)
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setUsePool(true);
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        System.out.println("JedisConnectionFactory bean init success.");
        return factory;
    }


//    @Bean
//    public RedisTemplate<?, ?> getRedisTemplate() {
//        JedisConnectionFactory factory = getConnectionFactory();
//        System.out.println(this.host+","+factory.getHostName()+","+factory.getDatabase());
////        factory.setHostName(this.host);
////        factory.setPassword(this.password);
//        RedisTemplate<?, ?> template = new StringRedisTemplate(getConnectionFactory());
//        return template;
//    }

}
