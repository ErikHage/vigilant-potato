package com.tfr.vigilant.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UuidUtilsTest {

    private final UuidUtils uuidUtils = new UuidUtils();

    @Test
    public void testGetUuid_ExpectString() {
        String uuid = uuidUtils.getUuid();

        assertNotNull(uuid);
    }

    @Test
    public void testGetUuid_GivenMultipleCalls_ExpectDifferentStrings() {
        String uuid1 = uuidUtils.getUuid();
        String uuid2 = uuidUtils.getUuid();

        assertNotEquals(uuid1, uuid2);
    }
}
