package com.turkcell.transactionservice.Service;

import com.turkcell.events.Transaction.ITransaction;
import com.turkcell.transactionservice.config.KafkaConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public TransactionEventProducer(KafkaTemplate<String, Object> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendEvent(ITransaction event){
        kafkaTemplate.send(KafkaConfiguration.SERVICENAME, event);
    }
}
