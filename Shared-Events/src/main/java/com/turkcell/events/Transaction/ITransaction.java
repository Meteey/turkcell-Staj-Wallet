package com.turkcell.events.Transaction;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TransactionCompletedEvent.class, name = "TransactionCompletedEvent"),
        @JsonSubTypes.Type(value = TransactionCancelledEvent.class, name = "TransactionCancelledEvent"),
        @JsonSubTypes.Type(value = PeriodicTransactionAppliedEvent.class, name = "PeriodicTransactionAppliedEvent"),
        @JsonSubTypes.Type(value = PeriodicTransactionCancelledEvent.class, name = "PeriodicTransactionCancelledEvent")

})
public sealed interface ITransaction permits PeriodicTransactionAppliedEvent, PeriodicTransactionCancelledEvent, TransactionCancelledEvent, TransactionCompletedEvent {}
