package com.example.orderservice.command.api.aggregate;

import com.example.commonservice.commands.CompleteOrderCommand;
import com.example.commonservice.events.OrderCompletedEvent;
import com.example.orderservice.command.api.command.CreateOrderCommand;
import com.example.orderservice.command.api.events.OrderCreatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private String orderStatus;

    @CommandHandler // Define a command handler(object that executes a command) in an Aggregate
    public OrderAggregate(CreateOrderCommand createOrderCommand){
        // 1. Create Event from command
        OrderCreatedEvent orderCreatedEvent =
                new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent); // instance copy (same name with same type)

        // 2. Apply domain event message
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler //
    public void on(OrderCreatedEvent event){
        this.orderId = event.getOrderId();
        this.productId = event.getProductId();
        this.userId = event.getUserId();
        this.addressId = event.getAddressId();
        this.quantity = event.getQuantity();
        this.orderStatus = event.getOrderStatus();
    }

    @CommandHandler
    public void handle(CompleteOrderCommand completeOrderCommand){
        // Validate the Command
        // Publish Order Completed Event

        OrderCompletedEvent orderCompletedEvent
                = OrderCompletedEvent.builder()
                .orderStatus(completeOrderCommand.getOrderStatus())
                .orderId(completeOrderCommand.getOrderId())
                .build();

        AggregateLifecycle.apply(orderCompletedEvent);
    }

    public void on(OrderCompletedEvent event){
        this.orderStatus = event.getOrderStatus();
    }
}
