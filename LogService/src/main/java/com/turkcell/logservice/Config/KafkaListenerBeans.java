package com.turkcell.logservice.Config;

import com.turkcell.events.Account.IAccount;
import com.turkcell.events.Auth.IAuth;
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

    @Bean(name = "authFactory")
    public ConcurrentKafkaListenerContainerFactory<String, IAuth> authFactory() {
        return factoryService.getFactory(IAuth.class, "log-service");
    }

    @Bean(name = "accountFactory")
    public ConcurrentKafkaListenerContainerFactory<String, IAccount> accountFactory() {
        return factoryService.getFactory(IAccount.class, "log-service");
    }
    @Bean(name = "transactionFactory")
    public ConcurrentKafkaListenerContainerFactory<String, ITransaction> transactionFactory() {
        return factoryService.getFactory(ITransaction.class, "log-service");
    }
    @Bean(name = "balanceFactory")
    public ConcurrentKafkaListenerContainerFactory<String, IBalance> balanceFactory() {
        return factoryService.getFactory(IBalance.class, "log-service");
    }
}
