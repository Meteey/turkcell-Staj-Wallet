package com.turkcell.authenticationservice.config;

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

    @Bean(name = "accountFactory")
    public ConcurrentKafkaListenerContainerFactory<String, IAccount> authFactory() {
        return factoryService.getFactory(IAccount.class, "auth-service");
    }

}
