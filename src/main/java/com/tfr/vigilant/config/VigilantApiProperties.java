package com.tfr.vigilant.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component("VigilantApiProperties")
@ConfigurationProperties("vigilant.api")
public class VigilantApiProperties {

    private String key = "localDefault";
    private Set<String> allowedServices = Set.of();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Set<String> getAllowedServices() {
        return allowedServices;
    }

    public void setAllowedServices(String allowedServices) {
        String[] split = allowedServices.split(",");
        this.allowedServices = new HashSet<>(Arrays.asList(split));
    }
}
