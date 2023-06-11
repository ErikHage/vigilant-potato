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
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MessageResponse> createRecipe(@RequestBody MessageRequest request) {
        logger.debug("endpoint: /messages/enqueue");

        try {
            Message message = parseRequest(request);
            messageQueue.add(message);
        } catch (IllegalArgumentException ex) {
            return errorResponse(HttpStatus.BAD_REQUEST, "Invalid message type provided");
        } catch (Exception ex) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage());
        }

        return successResponse();
    }

    private ResponseEntity<MessageResponse> successResponse() {
        MessageResponse messageResponse = new MessageResponse("message received and queued");
        return new ResponseEntity<>(messageResponse, HttpStatus.ACCEPTED);
    }

    private ResponseEntity<MessageResponse> errorResponse(HttpStatus status, String message) {
        MessageResponse messageResponse = new MessageResponse(message);
        return new ResponseEntity<>(messageResponse, status);
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
