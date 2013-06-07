package com.heaptrip.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.coobird.thumbnailator.Thumbnails;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.ImageEnum;
import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.domain.service.ImageStorageService;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

	private static final String FOLDER_NAME = "images";

	private static float RESIZE_QUALITY = 0.5f;

	@Autowired
	private MongoContext mongoContext;

	@Override
	public String saveImage(String fileName, ImageEnum imageType, InputStream is) throws IOException {
		DB db = mongoContext.getDatabase();
		GridFS fs = new GridFS(db, FOLDER_NAME);
		GridFSInputFile file = fs.createFile(resize(imageType, is));
		file.setFilename(fileName);
		file.save();
		return file.getId().toString();
	}

	@Override
	public InputStream getImage(String imageId) {
		DB db = mongoContext.getDatabase();
		GridFS fs = new GridFS(db, FOLDER_NAME);
		GridFSDBFile file = fs.find(new ObjectId(imageId));
		return (file == null) ? null : file.getInputStream();
	}

	@Override
	public void removeImage(String imageId) {
		DB db = mongoContext.getDatabase();
		GridFS fs = new GridFS(db, FOLDER_NAME);
		fs.remove(new ObjectId(imageId));
	}

	private InputStream resize(ImageEnum imageType, InputStream is) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Thumbnails.of(is).size(imageType.getWidth(), imageType.getHeight()).useOriginalFormat()
				.outputQuality(RESIZE_QUALITY).toOutputStream(os);
		InputStream result = new ByteArrayInputStream(os.toByteArray());
		return result;
	}

}
