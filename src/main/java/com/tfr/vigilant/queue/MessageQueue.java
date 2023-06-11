package com.tfr.vigilant.queue;


import com.tfr.vigilant.model.message.Message;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

@Component("MessageQueue")
public class MessageQueue {

    private static MessageQueue INSTANCE;

    private final Queue<Message> queue;

    private MessageQueue() {
        queue = new PriorityBlockingQueue<>();
    }

    public static MessageQueue getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MessageQueue();
        }

        return INSTANCE;
    }

    public void add(Message message) {
        queue.add(message);
    }

    public Message poll() {
        return queue.poll();
    }
}
