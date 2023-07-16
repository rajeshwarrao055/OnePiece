package com.personal.OnePiece.redis.pubsub;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RedisMessagePublisher implements MessagePublisher {

    public RedisTemplate<String, Object> redisTemplate;
    public ChannelTopic channelTopic;

    @Override
    public void publish(String message) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }
}
