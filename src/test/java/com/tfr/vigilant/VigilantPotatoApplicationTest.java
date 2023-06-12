package com.tfr.vigilant;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = VigilantPotatoApplication.class)
public class VigilantPotatoApplicationTest {

    @Test
    public void testApplicationContextLoads() {
        VigilantPotatoApplication.main(new String[] {});
    }
}
