package com.turkcell.accountservice.Adapter.Kafka;

import com.turkcell.accountservice.AccountService.AccountService;
import com.turkcell.events.Auth.IAuth;
import com.turkcell.events.Auth.UserCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


//TODO : her service için ayrı handler ve log tablosu
@Service
public class EventListener {
    private final AccountService accountService;
    public EventListener(AccountService accountService){
        this.accountService = accountService;
    }
    @KafkaListener(topics="auth-service-topic", groupId = "account-service", containerFactory = "authFactory")
    public void listenAuthEvents(IAuth authEvent){
        if(authEvent instanceof UserCreatedEvent event){
            accountService.createPendingAccountFromEvent(event);
        }
        else{
            System.out.println("hata : " + authEvent.getClass());
        }
    }


}
