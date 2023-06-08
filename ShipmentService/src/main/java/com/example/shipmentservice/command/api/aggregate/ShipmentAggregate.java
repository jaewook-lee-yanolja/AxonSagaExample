package com.example.shipmentservice.command.api.aggregate;

import com.example.commonservice.commands.ShipOrderCommand;
import com.example.commonservice.events.OrderShippedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class ShipmentAggregate {
    @AggregateIdentifier
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;

    public ShipmentAggregate(ShipOrderCommand shipOrderCommand){
        // validate the command
        // publish the order shipped event
        OrderShippedEvent orderShippedEvent
                = OrderShippedEvent.builder()
                .shipmentId(shipOrderCommand.getShipmentId())
                .orderId(shipOrderCommand.getOrderId())
                .shipmentStatus("COMPLETED")
                .build();

        AggregateLifecycle.apply(orderShippedEvent);

    }

    @EventSourcingHandler
    public void on(OrderShippedEvent event){
        this.orderId = event.getOrderId();
        this.shipmentId = event.getShipmentId();
        this.shipmentStatus = event.getShipmentStatus();
    }
}
