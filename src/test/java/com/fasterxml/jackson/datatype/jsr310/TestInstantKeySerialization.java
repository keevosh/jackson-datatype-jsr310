package com.fasterxml.jackson.datatype.jsr310;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestInstantKeySerialization {

    private static final String INSTANT_STRING = "2011-07-19T21:35:11.000000123Z";
    private static final Instant INSTANT = Instant.ofEpochSecond(1311111311l, 123);

    private ObjectMapper om;
    private Map<Instant, String> map;

    @Before
    public void setUp() {
        this.om = new ObjectMapper();
        om.registerModule(new JSR310Module());
        map = new HashMap<>();
    }

    /*
     * ObjectMapper configuration is not respected at deserialization and serialization at the moment.
     */

    @Test
    public void testSerialization0() throws Exception {
        map.put(Instant.ofEpochMilli(0), "test");

        String value = om.writeValueAsString(map);

        Assert.assertEquals("Value is incorrect", map("1970-01-01T00:00:00Z", "test"), value);
    }

    @Test
    public void testSerialization1() throws Exception {
        map.put(INSTANT, "test");

        String value = om.writeValueAsString(map);

        assertEquals("Value is incorrect", map(INSTANT_STRING, "test"), value);
    }

    @Test
    public void testDeserialization0() throws Exception {
        Map<Instant, String> value = om.readValue(map("1970-01-01T00:00:00Z", "test"), new TypeReference<Map<Instant, String>>() {
        });

        map.put(Instant.ofEpochMilli(0), "test");
        assertEquals("Value is incorrect", map, value);
    }

    @Test
    public void testDeserialization1() throws Exception {
        Map<Instant, String> value = om.readValue(map(INSTANT_STRING, "test"), new TypeReference<Map<Instant, String>>() {
        });

        map.put(INSTANT, "test");
        assertEquals("Value is incorrect", map, value);
    }

    private String map(String key, String value) {
        return String.format("{\"%s\":\"%s\"}", key, value);
    }

}
