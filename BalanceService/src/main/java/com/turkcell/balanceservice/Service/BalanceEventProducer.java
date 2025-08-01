package com.turkcell.balanceservice.Service;

import com.turkcell.balanceservice.Config.KafkaConfiguration;
import com.turkcell.events.Balance.IBalance;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BalanceEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BalanceEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(IBalance event){
        kafkaTemplate.send(KafkaConfiguration.SERVICENAME, event);
    }}
