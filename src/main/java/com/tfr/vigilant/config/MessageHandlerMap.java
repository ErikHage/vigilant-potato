package com.tfr.vigilant.config;

import com.tfr.vigilant.handler.MessageHandler;
import com.tfr.vigilant.handler.TestHighPriorityMessageHandler;
import com.tfr.vigilant.handler.TestLowPriorityMessageHandler;
import com.tfr.vigilant.model.message.MessageType;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("MessageHandlerMap")
public class MessageHandlerMap extends HashMap<MessageType, MessageHandler> {

    public MessageHandlerMap() {
        this.put(MessageType.TEST_HP, new TestHighPriorityMessageHandler());
        this.put(MessageType.TEST_LP, new TestLowPriorityMessageHandler());
    }
}
