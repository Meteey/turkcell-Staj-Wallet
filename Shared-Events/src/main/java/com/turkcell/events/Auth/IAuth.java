package com.turkcell.events.Auth;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserCreatedEvent.class, name = "UserCreatedEvent"),
        @JsonSubTypes.Type(value = UserDeletedEvent.class, name = "UserDeletedEvent"),
        @JsonSubTypes.Type(value = UserLoggedInEvent.class, name = "UserLoggedInEvent"),
        @JsonSubTypes.Type(value = UserLoggedOutEvent.class, name = "UserLoggedOutEvent"),
        @JsonSubTypes.Type(value = UserChangedPasswordEvent.class, name = "UserChangedPasswordEvent")
})
public sealed interface IAuth permits UserCreatedEvent, UserDeletedEvent, UserChangedPasswordEvent, UserLoggedInEvent, UserLoggedOutEvent {}
