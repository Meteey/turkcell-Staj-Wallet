package com.turkcell.accountservice.Config;

import com.turkcell.events.Account.IAccount;
import com.turkcell.events.Auth.IAuth;
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
        return factoryService.getFactory(IAuth.class, "account-service");
    }

}
