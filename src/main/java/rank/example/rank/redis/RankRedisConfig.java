package rank.example.rank.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RankRedisConfig extends DefaultRedisConfig{
    @Bean
    @Primary
    public RedisConnectionFactory rankRedisConnectionFactory() {
        return createLettuceConnectionFactory(0);  // Redis DB 선택
    }

    @Bean
    @Qualifier("rankRedisTemplate")
    public StringRedisTemplate rankRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setConnectionFactory(rankRedisConnectionFactory());
        return template;
    }
}
