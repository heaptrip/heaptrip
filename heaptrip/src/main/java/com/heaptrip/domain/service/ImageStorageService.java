package com.heaptrip.domain.service;

import java.io.InputStream;

import com.heaptrip.domain.entity.ImageEnum;

public interface ImageStorageService {

	public String saveImage(String fileName, ImageEnum imageType, InputStream is);

	public InputStream getImage(String imageId);

	public void removeImage(String imageId);
}
