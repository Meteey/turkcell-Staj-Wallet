package com.turkcell.balanceservice.Config;

import com.turkcell.events.Balance.IBalance;
import com.turkcell.events.Transaction.ITransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
public class KafkaListenerBeans {

    @Autowired
    private GenericKafkaListenerFactoryService factoryService;

    @Bean(name = "transactionFactory")
    public ConcurrentKafkaListenerContainerFactory<String, ITransaction> transactionFactory() {
        return factoryService.getFactory(ITransaction.class, "balance-service");
    }


}
