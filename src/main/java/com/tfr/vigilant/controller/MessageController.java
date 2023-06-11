package com.tfr.vigilant.controller;

import com.tfr.vigilant.model.message.Message;
import com.tfr.vigilant.model.message.MessageRequest;
import com.tfr.vigilant.model.message.MessageResponse;
import com.tfr.vigilant.model.message.MessageType;
import com.tfr.vigilant.queue.MessageQueue;
import com.tfr.vigilant.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class MessageController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MessageQueue messageQueue;

    public MessageController(@Qualifier("MessageQueue") MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @RequestMapping(value = "/messages/enqueue",
            consumes = Constants.APPLICATION_JSON,
            produces = Constants.APPLICATION_JSON,
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MessageResponse createRecipe(@RequestBody MessageRequest request) {
        logger.debug("endpoint: /messages/enqueue");

        Message message = parseRequest(request);

        messageQueue.add(message);

        return new MessageResponse("message received and queued");
    }

    private Message parseRequest(MessageRequest messageRequest) {
        MessageType messageType = MessageType.valueOf(messageRequest.getType());
        String content = messageRequest.getContent();
        LocalDateTime receivedAt = LocalDateTime.now();
        String messageId = UUID.randomUUID().toString();

        Message message =  new Message(messageId, messageType, content, receivedAt);

        logger.debug("Queueing message: " + message);

        return message;
    }
}
