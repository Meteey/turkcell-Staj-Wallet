package com.turkcell.balanceservice.Config;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

public class GenericKafkaListenerFactory {

    public static <T> ConcurrentKafkaListenerContainerFactory<String, T> createListenerFactory(Class<T> generic, String groupId) {
        ConsumerFactory<String, T> consumerFactory = GenericKafkaFactoryProvider.createConsumerFactory(generic, groupId);
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
