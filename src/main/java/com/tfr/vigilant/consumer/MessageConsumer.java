package com.tfr.vigilant.consumer;

import com.tfr.vigilant.model.message.Message;
import com.tfr.vigilant.queue.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component("MessageConsumer")
public class MessageConsumer implements ApplicationListener<ContextRefreshedEvent>, Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MessageQueue messageQueue;

    public MessageConsumer(@Qualifier("MessageQueue") MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    private void consumeMessage() {
        Message message = messageQueue.poll();
        logger.debug(String.format("Consuming message of type [%s], with id [%s]",
                message.messageType(), message.messageId()));

        // handle the message
    }

    @Override
    public void run() {
        while (true) {
            if (messageQueue.isEmpty()) {
                try {
                    logger.debug("No messages to handle, sleeping 5 seconds");
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    logger.error("Consumer thread interrupted", ex);
                    throw new RuntimeException(ex);
                }
            } else {
                consumeMessage();
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Message Consumer Initializing");

        new Thread(this).start();

        logger.info("Message Consumer Initialized");
    }
}
