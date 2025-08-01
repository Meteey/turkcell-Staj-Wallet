package com.turkcell.accountservice.AccountService;

import com.turkcell.accountservice.Config.KafkaConfiguration;
import com.turkcell.events.Account.IAccount;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class AccountEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AccountEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(IAccount event) {

        kafkaTemplate.send(KafkaConfiguration.SERVICENAME, event);


    }
}