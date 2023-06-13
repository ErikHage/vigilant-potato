package com.tfr.vigilant.model.message;

import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record Message(String messageId,
                      MessageType messageType,
                      String content,
                      LocalDateTime receivedAt) implements Comparable<Message> {

    @Override
    public int compareTo(Message other) {
        if (receivedAt.isBefore(other.receivedAt)) {
            return -1;
        } else if (receivedAt.isAfter(other.receivedAt)) {
            return 1;
        }

        return 0;
    }
}
