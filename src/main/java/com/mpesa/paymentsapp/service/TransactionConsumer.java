package com.mpesa.paymentsapp.service;

import com.mpesa.paymentsapp.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionConsumer {

    @RabbitListener(queues = RabbitMQConfig.TRANSACTION_QUEUE)
    public void consume(String message) {
        log.info("Transaction event received: {}", message);
    }
}
