package com.example.paymentservice.command.api.events;

import com.example.commonservice.events.PaymentProcessedEvent;
import com.example.paymentservice.command.api.data.Payment;
import com.example.paymentservice.command.api.data.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class PaymentEventHandler {
    private PaymentRepository paymentRepository;


    @EventHandler
    public void on(PaymentProcessedEvent event){
        Payment payment = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .paymentStatus("COMPLETED")
                .timeStamp(new Date())
                .build();

        paymentRepository.save(payment);
    }
}
