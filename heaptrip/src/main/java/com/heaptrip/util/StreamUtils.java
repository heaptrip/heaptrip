package com.heaptrip.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class StreamUtils {

	public static InputStream getResetableInputStream(InputStream is) throws IOException {
		if (is.markSupported()) {
			return is;
		} else {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(is, baos);
			byte[] bytes = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			return bais;
		}
	}
}
