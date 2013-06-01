package com.heaptrip.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.ImageEnum;
import com.heaptrip.domain.service.ImageStorageService;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

	@Override
	public String saveImage(String fileName, ImageEnum imageType, InputStream is) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getImage(String imageId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeImage(String imageId) {
		// TODO Auto-generated method stub
	}

}
