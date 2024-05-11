package com.tfr.vigilant.handler;

import com.tfr.vigilant.config.MessageHandlerPriorities;
import com.tfr.vigilant.model.message.MessageType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("MessageHandlerFactory")
public class MessageHandlerFactory {

    private final MessageHandlerPriorities handlers;

    public MessageHandlerFactory(@Qualifier("MessageHandlerPriorities") MessageHandlerPriorities handlers) {
        this.handlers = handlers;
    }

    public MessageHandler getHandler(MessageType messageType) {
        return handlers.get(messageType);
    }
}
