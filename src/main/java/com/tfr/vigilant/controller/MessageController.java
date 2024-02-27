package com.tfr.vigilant.controller;

import com.tfr.vigilant.config.VigilantApiProperties;
import com.tfr.vigilant.model.exception.UnauthorizedException;
import com.tfr.vigilant.model.message.Message;
import com.tfr.vigilant.model.message.MessageRequest;
import com.tfr.vigilant.model.message.MessageResponse;
import com.tfr.vigilant.model.message.MessageType;
import com.tfr.vigilant.queue.MessageQueue;
import com.tfr.vigilant.utils.Constants;
import com.tfr.vigilant.utils.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class MessageController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MessageQueue messageQueue;
    private final UuidUtils uuidUtils;
    private final VigilantApiProperties apiProperties;

    public MessageController(@Qualifier("MessageQueue") MessageQueue messageQueue,
                             @Qualifier("UuidUtils") UuidUtils uuidUtils,
                             @Qualifier("VigilantApiProperties") VigilantApiProperties apiProperties) {
        this.messageQueue = messageQueue;
        this.uuidUtils = uuidUtils;
        this.apiProperties = apiProperties;
    }

    @RequestMapping(value = "/messages/enqueue",
            consumes = Constants.APPLICATION_JSON,
            produces = Constants.APPLICATION_JSON,
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<MessageResponse> acceptMessage(
            @RequestHeader(value="vigilant-service") String vigilantService,
            @RequestHeader(value="vigilant-key") String vigilantKey,
            @RequestBody MessageRequest request) {
        logger.debug("endpoint: /vigilant/messages/enqueue");

        try {
            assertApiKey(vigilantService, vigilantKey);
            Message message = parseRequest(request);
            messageQueue.add(message);
            return successResponse(message);
        } catch (UnauthorizedException ex) {
            return errorResponse(HttpStatus.UNAUTHORIZED, "Request Unauthorized");
        } catch (IllegalArgumentException ex) {
            return errorResponse(HttpStatus.BAD_REQUEST, "Invalid message type provided");
        } catch (Exception ex) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage());
        }
    }

    private void assertApiKey(String service, String key) throws UnauthorizedException {
        System.out.println("api key: " + apiProperties.getKey());
        System.out.println("allowedServices: " + apiProperties.getAllowedServices());

        if (service != null &&
                apiProperties.getAllowedServices().contains(service) &&
                apiProperties.getKey().equals(key)) {
            return;
        }

        throw new UnauthorizedException();
    }

    private ResponseEntity<MessageResponse> successResponse(Message message) {
        MessageResponse messageResponse = new MessageResponse(message.messageId(), "message received and queued");
        return new ResponseEntity<>(messageResponse, HttpStatus.ACCEPTED);
    }

    private ResponseEntity<MessageResponse> errorResponse(HttpStatus status, String message) {
        MessageResponse messageResponse = new MessageResponse("NA", message);
        return new ResponseEntity<>(messageResponse, status);
    }

    private Message parseRequest(MessageRequest messageRequest) {
        MessageType messageType = MessageType.valueOf(messageRequest.getType());
        String content = messageRequest.getContent();
        LocalDateTime receivedAt = LocalDateTime.now();
        String messageId = uuidUtils.getUuid();

        Message message =  new Message(messageId, messageType, content, receivedAt);

        logger.debug("Queueing message: " + message);

        return message;
    }
}
