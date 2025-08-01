package com.turkcell.events.Balance;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BalanceDecreasedEvent.class, name = "BalanceDecreasedEvent"),
        @JsonSubTypes.Type(value = BalanceIncreasedEvent.class, name = "BalanceIncreasedEvent"),

})
public sealed interface IBalance permits BalanceIncreasedEvent, BalanceDecreasedEvent{}
