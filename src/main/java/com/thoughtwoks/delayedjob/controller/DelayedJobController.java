package com.thoughtwoks.delayedjob.controller;

import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.QueueClient;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class DelayedJobController {

    private final QueueClient queueClient;

    private final int delayedSecond = 20;

    @PostMapping("/post")
    public void postMessage(@RequestParam String messageBody) throws InterruptedException, ServiceBusException {
        Message message = new Message(messageBody.getBytes(StandardCharsets.UTF_8));
        long seq = queueClient.scheduleMessage(message, Instant.now().plusSeconds(delayedSecond));
        log.info("Sending message: " + seq);
    }

    @PostMapping("/cancel")
    public void cancelMessage(@RequestParam Long seqNum) throws ServiceBusException, InterruptedException {
        queueClient.cancelScheduledMessage(seqNum);
    }
}
