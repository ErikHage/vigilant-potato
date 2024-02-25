package com.tfr.vigilant.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("VigilantApiProperties")
@ConfigurationProperties("vigilant.api")
public class VigilantApiProperties {

    private String key = "localDefault";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
