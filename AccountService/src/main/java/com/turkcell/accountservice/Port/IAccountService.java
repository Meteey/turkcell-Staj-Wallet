package com.turkcell.accountservice.Port;

import com.turkcell.accountservice.Domain.Entities.Account;
import com.turkcell.events.Auth.UserCreatedEvent;

public interface IAccountService {
    void pendUser(UserCreatedEvent event);
    void activeUser(Account account);
}
