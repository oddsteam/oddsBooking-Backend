package team.odds.booking.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.odds.booking.service.QueueReceiverService;

@Configuration
public class MessageQueuingConfig {
    @Bean
    public TopicExchange topic() {
        return new TopicExchange("odds.booking");
    }

    @Bean
    public Queue queue() {
        return new Queue("odds-booking-message", false);
    }

    @Bean
    public Binding binding(TopicExchange topic, Queue queue) {
        return BindingBuilder.bind(queue).to(topic).with("odds.booking.#");
    }

    @Bean
    MessageListenerAdapter listenerAdapter(QueueReceiverService receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("odds-booking-message");
        container.setMessageListener(listenerAdapter);
        return container;
    }

}
