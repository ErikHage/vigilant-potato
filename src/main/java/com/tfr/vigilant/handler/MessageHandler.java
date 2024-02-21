package com.tfr.vigilant.handler;

import com.tfr.vigilant.model.message.Message;

import java.util.Map;

public interface MessageHandler {

    Map<String, Object> handle(Message message);
}
