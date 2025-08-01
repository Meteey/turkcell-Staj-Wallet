package com.turkcell.authenticationservice.adapter.in;

import com.turkcell.authenticationservice.AuthService.AuthService;
import com.turkcell.events.Account.AccountDeletedEvent;
import com.turkcell.events.Account.IAccount;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


//TODO : her service için ayrı handler ve log tablosu
@Service
public class EventListener {
    private final AuthService authService;
    public EventListener(AuthService authService){
        this.authService = authService;
    }
    @KafkaListener(topics="account-service-topic", groupId = "auth-service", containerFactory = "accountFactory")
    public void listenAuthEvents(IAccount accountEvent){
        if(accountEvent instanceof AccountDeletedEvent event){

            authService.deleteUser(event.userId(), event.ip());
        }
        else{
            System.out.println("İşlenmeyecek event toplandı : " + accountEvent.getClass());
        }
    }


}
