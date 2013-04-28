package com.heaptrip.domain.service;

import java.io.InputStream;

public interface FileService {

	public String upload(String fileName, Long fileSize, InputStream is);

	public InputStream getFileBody(String fileId);
}
