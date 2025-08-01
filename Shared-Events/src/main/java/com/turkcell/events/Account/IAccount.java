package com.turkcell.events.Account;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.turkcell.events.Auth.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AccountPendingEvent.class, name = "AccountPendingEvent"),
        @JsonSubTypes.Type(value = AccountActivatedEvent.class, name = "AccountActivatedEvent"),
        @JsonSubTypes.Type(value = AccountDeletedEvent.class, name = "AccountDeletedEvent"),
        @JsonSubTypes.Type(value = AccountUpdatedEvent.class, name = "AccountUpdatedEvent"),


})
public sealed interface IAccount permits AccountActivatedEvent, AccountDeletedEvent, AccountPendingEvent, AccountUpdatedEvent {
}
