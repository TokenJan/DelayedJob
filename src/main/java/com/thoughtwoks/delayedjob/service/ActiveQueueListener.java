package com.thoughtwoks.delayedjob.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActiveQueueListener {

    private static final String QUEUE_NAME = "<your-queue-name>";

    @JmsListener(destination = QUEUE_NAME, containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(byte[] message) {
        log.info("Received message from queue: {}", new String(message));
    }
}
