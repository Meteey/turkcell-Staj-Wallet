package com.turkcell.transactionservice.config;

import com.turkcell.events.Account.IAccount;
import com.turkcell.events.Auth.IAuth;
import com.turkcell.events.Balance.IBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
public class KafkaListenerBeans {

    @Autowired
    private GenericKafkaListenerFactoryService factoryService;

    @Bean(name = "balanceFactory")
    public ConcurrentKafkaListenerContainerFactory<String, IBalance> balanceFactory() {
        return factoryService.getFactory(IBalance.class, "transaction-service");
    }


}
