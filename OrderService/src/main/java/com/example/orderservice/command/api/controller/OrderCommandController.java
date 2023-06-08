package com.example.orderservice.command.api.controller;

import com.example.orderservice.command.api.command.CreateOrderCommand;
import com.example.orderservice.command.api.model.OrderRestModel;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderCommandController {

    private final CommandGateway commandGateway; // interface towards the Command dispatching mechanism

    @PostMapping
    public String createOrder(@RequestBody OrderRestModel orderRestModel){


        String orderId = UUID.randomUUID().toString();

        CreateOrderCommand createOrderCommand
                = CreateOrderCommand.builder()
                .orderId(orderId)
                .addressId(orderRestModel.getAddressId())
                .productId(orderRestModel.getProductId())
                .quantity(orderRestModel.getQuantity())
                .orderStatus("CREATED")
                .build();

        commandGateway.sendAndWait(createOrderCommand); // synchronous call
        // to use asynchronous call
//        commandGateway.send(createOrderCommand);
        return "Order Created";
    }
}
