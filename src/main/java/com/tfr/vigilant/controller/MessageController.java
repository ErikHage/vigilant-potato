package com.tfr.vigilant.controller;

import com.tfr.vigilant.model.message.Message;
import com.tfr.vigilant.model.message.MessageResponse;
import com.tfr.vigilant.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/messages/enqueue",
            consumes = Constants.APPLICATION_JSON,
            produces = Constants.APPLICATION_JSON,
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MessageResponse createRecipe(@RequestBody Message message) {
        logger.debug("endpoint: /messages/enqueue");

        logger.debug("message: " + message);

        return new MessageResponse("message received");
    }
}
