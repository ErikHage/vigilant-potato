package com.tfr.vigilant.queue;


import com.tfr.vigilant.model.message.Message;
import com.tfr.vigilant.model.message.MessagePriority;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

@Component("MessageQueue")
public class MessageQueue {

    private static MessageQueue INSTANCE;

    private final Queue<Message> queue;
    private final Queue<Message> priorityQueue;
    private final Map<String, String> messageStatuses;

    private MessageQueue() {
        queue = new PriorityBlockingQueue<>();
        priorityQueue = new PriorityBlockingQueue<>();
        messageStatuses = new ConcurrentHashMap<>();
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
        messageStatuses.put(message.messageId(), "QUEUED");
    }

    public Message poll() {
        Message message;

        if (!priorityQueue.isEmpty()) {
             message = priorityQueue.poll();
        } else {
            message = queue.poll();
        }

        if (message != null) {
            messageStatuses.remove(message.messageId());
        }

        return message;
    }

    public String getMessageStatus(String messageId) {
        return messageStatuses.get(messageId);
    }

    public int size() {
        return queue.size() + priorityQueue.size();
    }

    public int lowPrioritySize() {
        return queue.size();
    }

    public int highPrioritySize() {
        return priorityQueue.size();
    }

    public void clear() {
        queue.clear();
        priorityQueue.clear();
        messageStatuses.clear();
    }
}
