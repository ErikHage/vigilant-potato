package com.tfr.vigilant.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("UuidUtils")
public class UuidUtils {

    public String getUuid() {
        return UUID.randomUUID().toString();
    }
}
