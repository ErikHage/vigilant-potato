package com.tfr.vigilant.handler;

import com.tfr.vigilant.config.MessageHandlerMap;
import com.tfr.vigilant.model.message.MessageType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("MessageHandlerFactory")
public class MessageHandlerFactory {

    private final MessageHandlerMap handlers;

    public MessageHandlerFactory(@Qualifier("MessageHandlerMap") MessageHandlerMap handlers) {
        this.handlers = handlers;
    }

    public MessageHandler getHandler(MessageType messageType) {
        return handlers.get(messageType);
    }
}
