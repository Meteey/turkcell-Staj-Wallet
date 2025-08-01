package com.turkcell.authenticationservice.AuthService;
import com.turkcell.authenticationservice.config.KafkaConfiguration;
import com.turkcell.events.Auth.IAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthEventProducer {
    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public AuthEventProducer(KafkaTemplate<String, Object> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent( IAuth event) {
        kafkaTemplate.send(KafkaConfiguration.SERVICENAME, event);

    }

}





