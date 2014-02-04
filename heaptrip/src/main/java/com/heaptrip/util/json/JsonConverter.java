package com.heaptrip.util.json;

import com.heaptrip.web.controller.base.RestException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

public class JsonConverter {

    public <T> T JSONToObject(String json, Class<T> clazz) {

        T result = null;

        try {
            result = new ObjectMapper().readValue(json, clazz);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;

    }

    public String objectToJSON(Object object) {
        ObjectWriter ow = new ObjectMapper().writer();
        try {
            return ow.writeValueAsString(object);
        } catch (IOException e) {
            throw new RestException(e);
        }
    }

}
