package com.example.paymentservice.command.api.aggregate;

import com.example.commonservice.commands.ValidatePaymentCommand;
import com.example.commonservice.events.PaymentProcessedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
@Slf4j
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus;

    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand){
        log.info("Executing ValidatePaymentCommand for " +
                "Order Id: {} and Payment Id: {}",
                validatePaymentCommand.getOrderId(),
                validatePaymentCommand.getPaymentId());

        // validate the payment detail
        // publish the payment processed event

        PaymentProcessedEvent paymentProcessedEvent
                = new PaymentProcessedEvent(
                validatePaymentCommand.getPaymentId(), validatePaymentCommand.getOrderId()
        );

        AggregateLifecycle.apply(paymentProcessedEvent);

        log.info("PaymentProcessedEvent Applied");
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event){
        this.paymentId = event.getPaymentId();
        this.orderId = event.getOrderId();
    }
}
