package org.x1c1b.poll4u;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() { }

    public static String toJson(Object object) {

        ObjectMapper mapper = new ObjectMapper();

        try {

            return mapper.writeValueAsString(object);

        } catch (JsonProcessingException exc) {

            throw new RuntimeException("Failed to convert object to JSON string", exc);
        }
    }

    public static <T> T toObject(String json, Class<T> clazz) {

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, clazz);

        } catch (IOException exc) {

            LOGGER.error("Failed to convert json string `" + json + "` to class `" + clazz.getName() + "`", exc);
            return null;
        }
    }
}
