package com.turkcell.transactionservice.config;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class GenericKafkaListenerFactoryService {
    public <T> ConcurrentKafkaListenerContainerFactory<String, T> getFactory(Class<T> generic, String groupId) {
        return GenericKafkaListenerFactory.createListenerFactory(generic, groupId);
    }
}
