package com.example.orderservice.command.api.events;

import com.example.commonservice.events.OrderCompletedEvent;
import com.example.orderservice.command.api.data.Order;
import com.example.orderservice.command.api.data.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventsHandler {
    private OrderRepository orderRepository;

    @EventHandler
    public void on(OrderCreatedEvent event){
        Order order = new Order();
        BeanUtils.copyProperties(event, order);
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCompletedEvent event){
        Order order
                = orderRepository.findById(event.getOrderId()).get();

        order.setOrderStatus(event.getOrderStatus());

        orderRepository.save(order);
    }

}
