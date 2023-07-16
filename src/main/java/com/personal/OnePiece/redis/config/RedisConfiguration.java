package com.personal.OnePiece.redis.config;

import com.personal.OnePiece.redis.pubsub.MessagePublisher;
import com.personal.OnePiece.redis.pubsub.RedisMessagePublisher;
import com.personal.OnePiece.redis.pubsub.RedisMessageSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfiguration {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new RedisMessageSubscriber());
    }

    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("messageQueue");
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            JedisConnectionFactory jedisConnectionFactory, MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(jedisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, channelTopic());
        return redisMessageListenerContainer;
    }

    @Bean
    public MessagePublisher messagePublisher(RedisTemplate<String, Object> redisTemplate, ChannelTopic channelTopic) {
        return new RedisMessagePublisher(redisTemplate, channelTopic);
    }

}
