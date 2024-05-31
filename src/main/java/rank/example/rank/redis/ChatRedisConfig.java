//package rank.example.rank.redis;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import rank.example.rank.domain.chat.entity.ChatMessage;
//
//@Configuration
//public class ChatRedisConfig extends DefaultRedisConfig {
//    @Bean
//    public RedisConnectionFactory chatRedisConnectionFactory() {
//        return createLettuceConnectionFactory(1);  // Redis DB 선택
//    }
//
//    @Bean
//    @Qualifier("chatRedisTemplate")
//    public RedisTemplate<String, ChatMessage> chatRedisTemplate() {
//        RedisTemplate<String, ChatMessage> template = new RedisTemplate<>();
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
//        template.setConnectionFactory(chatRedisConnectionFactory());
//        return template;
//    }
//}