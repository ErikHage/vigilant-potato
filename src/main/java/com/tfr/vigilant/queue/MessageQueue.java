package com.tfr.vigilant.queue;


import com.tfr.vigilant.model.message.Message;
import com.tfr.vigilant.model.message.MessagePriority;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

@Component("MessageQueue")
public class MessageQueue {

    private static MessageQueue INSTANCE;

    private final Queue<Message> queue;
    private final Queue<Message> priorityQueue;

    private MessageQueue() {
        queue = new PriorityBlockingQueue<>();
        priorityQueue = new PriorityBlockingQueue<>();
    }

    public static MessageQueue getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MessageQueue();
        }

        return INSTANCE;
    }

    public void add(Message message) {
        if (message.messageType().getPriority() == MessagePriority.HIGH) {
            priorityQueue.add(message);
        } else {
            queue.add(message);
        }
    }

    public Message poll() {
        if (!priorityQueue.isEmpty()) {
            return priorityQueue.poll();
        }
        return queue.poll();
    }
}
