package com.mpesa.paymentsapp.service;

import com.mpesa.paymentsapp.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendTransaction(String msg, String transRefNo) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TRANSACTION_QUEUE, msg);
    }
}
