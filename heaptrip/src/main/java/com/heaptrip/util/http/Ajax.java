package com.heaptrip.util.http;

import java.util.HashMap;
import java.util.Map;

public class Ajax {

    private static final String KEY_DATA = "data";
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";

    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_ERROR = "error";

    public static Map<String, ? extends Object> successResponse(Object object) {
        Map<String, Object> map = new HashMap();
        map.put(KEY_DATA, object);
        map.put(KEY_STATUS, STATUS_SUCCESS);
        return map;
    }

    public static Map<String, ? extends Object> successResponse(Map<String, ? extends Object> objects) {
        Map<String, Object> map = new HashMap();
        map.put(KEY_DATA, objects);
        map.put(KEY_STATUS, STATUS_SUCCESS);
        return map;
    }

    public static Map<String, ? extends Object> emptyResponse() {
        Map<String, Object> map = new HashMap();
        map.put(KEY_STATUS, STATUS_SUCCESS);
        return map;
    }

    public static Map<String, ? extends Object> errorResponse(String message) {
        Map<String, Object> map = new HashMap();
        map.put(KEY_MESSAGE, message);
        map.put(KEY_STATUS, STATUS_ERROR);
        return map;

    }

    public static Map<String, ? extends Object> errorResponse(Throwable exception) {
        Map<String, Object> map = new HashMap();
        map.put(KEY_MESSAGE, exception.getLocalizedMessage());
        map.put(KEY_STATUS, STATUS_ERROR);
        return map;
    }

    public static Map<String, ? extends Object> errorResponse(String message, Map<String, ? extends Object> objects) {
        Map<String, Object> map = new HashMap();
        map.put(KEY_STATUS, STATUS_ERROR);
        map.put(KEY_MESSAGE, message);
        map.put(KEY_DATA, objects);
        return map;
    }

    public static Map<String, ? extends Object> errorResponse(String message, Object object) {
        Map<String, Object> map = new HashMap();
        map.put(KEY_MESSAGE, message);
        map.put(KEY_STATUS, STATUS_ERROR);
        map.put(KEY_DATA, object);
        return map;
    }

}
