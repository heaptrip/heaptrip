package com.heaptrip.util.stream;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {

    public static InputStream getResetableInputStream(InputStream is) throws IOException {
        if (is.markSupported()) {
            return is;
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(is, baos);
            byte[] bytes = baos.toByteArray();
            return new ByteArrayInputStream(bytes);
        }
    }

    public static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(is, baos);
        return baos.toByteArray();
    }
}
