//package rank.example.rank.redis;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//
//@Configuration
//public class DefaultRedisConfig {
//
//    @Value("redis.host")
//    private String redisHost;
//
//    @Value("redis.port")
//    private String redisPort;
//
//    public RedisConnectionFactory createLettuceConnectionFactory(int dbIndex) {
//        final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName("localhost");
//        redisStandaloneConfiguration.setPort(6379);
//        redisStandaloneConfiguration.setDatabase(dbIndex);
//        return new LettuceConnectionFactory(redisStandaloneConfiguration);
//    }
//}
