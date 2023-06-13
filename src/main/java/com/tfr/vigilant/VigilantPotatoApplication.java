package com.tfr.vigilant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VigilantPotatoApplication {
    private static final Logger logger = LoggerFactory.getLogger(VigilantPotatoApplication.class);

    public static void main(String[] args) {
        logger.debug("Starting application");

        try {
            SpringApplication.run(VigilantPotatoApplication.class, args);
        } catch(Exception ex) {
            logger.error("Error encountered while running application", ex);
            throw new RuntimeException(ex);
        }
    }
}