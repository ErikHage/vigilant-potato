package com.tfr.vigilant.queue;


import com.tfr.vigilant.model.message.Message;
import com.tfr.vigilant.model.message.MessagePriority;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

@Component(value = "MessageQueue")
public class MessageQueue {

    private final Queue<Message> queue;
    private final Queue<Message> priorityQueue;
    private final Map<String, String> messageStatuses;

    public MessageQueue() {
        queue = new PriorityBlockingQueue<>();
        priorityQueue = new PriorityBlockingQueue<>();
        messageStatuses = new ConcurrentHashMap<>();
    }

    public void add(Message message) {
        if (message.messageType().getPriority() == MessagePriority.HIGH) {
            priorityQueue.add(message);
        } else {
            queue.add(message);
        }
        messageStatuses.put(message.messageId(), "QUEUED");
    }

    /**
     * Returns true if there are no messages in either the high or normal
     * priority queues, otherwise returns false.
     * @return boolean
     */
    public boolean isEmpty() {
        return priorityQueue.isEmpty() && queue.isEmpty();
    }

    /**
     * Get the next item from the queue, checking the high priority queue first,
     * then checking the normal priority queue. If no message is present,
     * returns null.
     *
     * @return Message
     */
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

    /**
     * Get the status fo the message with the given id
     * @param messageId String
     * @return String
     */
    public String getMessageStatus(String messageId) {
        return messageStatuses.get(messageId);
    }

    /**
     * Get the current size of the message queue
     * @return int
     */
    public int size() {
        return queue.size() + priorityQueue.size();
    }

    /**
     * Get the current size of the low priority message queue
     * @return int
     */
    public int lowPrioritySize() {
        return queue.size();
    }

    /**
     * Get the current size of the high priority message queue
     * @return int
     */
    public int highPrioritySize() {
        return priorityQueue.size();
    }

    /**
     * Clear the message queue
     */
    public void clear() {
        queue.clear();
        priorityQueue.clear();
        messageStatuses.clear();
    }
}
