package com.heaptrip.web.controller.resource;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;

@Controller
public class ImageController extends ExceptionHandlerControler {

	
	@Autowired
	private ImageService imageStorageService;
	
	@RequestMapping("/image")
	public ResponseEntity<byte[]> getImage(@RequestParam(value = "imageId") String imageId) throws IOException {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		InputStream in = imageStorageService.getImage(imageId);
		return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
	}
	
}
