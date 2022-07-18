package team.odds.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueueProducerService {
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange topicExchange;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(topicExchange.getName(), "odds.booking.message", message);
    }
}
