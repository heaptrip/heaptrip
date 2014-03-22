package com.heaptrip.service.image;

import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.domain.service.image.ImageService;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import net.coobird.thumbnailator.Thumbnails;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageServiceImpl implements ImageService {

    private static final String FOLDER_NAME = "images";

    private static float RESIZE_QUALITY = 0.9f;

    @Autowired
    private MongoContext mongoContext;

    @Override
    public String saveImage(String fileName, InputStream is) throws IOException {
        Assert.notNull(fileName, "fileName must not be null");
        Assert.notNull(is, "inputStream must not be null");
        DB db = mongoContext.getDatabase();
        GridFS fs = new GridFS(db, FOLDER_NAME);
        GridFSInputFile file = fs.createFile(is);
        file.setFilename(fileName);
        file.save();
        return file.getId().toString();
    }

    @Override
    public String saveImage(String fileName, ImageEnum imageType, InputStream is) throws IOException {
        Assert.notNull(fileName, "fileName must not be null");
        Assert.notNull(imageType, "imageType must not be null");
        Assert.notNull(is, "inputStream must not be null");
        DB db = mongoContext.getDatabase();
        GridFS fs = new GridFS(db, FOLDER_NAME);
        GridFSInputFile file = fs.createFile(resize(imageType, is));
        file.setFilename(fileName);
        file.save();
        return file.getId().toString();
    }

    @Override
    public InputStream getImage(String imageId) {
        Assert.notNull(imageId, "imageId must not be null");
        DB db = mongoContext.getDatabase();
        GridFS fs = new GridFS(db, FOLDER_NAME);
        GridFSDBFile file = fs.find(new ObjectId(imageId));
        return (file == null) ? null : file.getInputStream();
    }

    @Override
    public void removeImage(String imageId) {
        Assert.notNull(imageId, "imageId must not be null");
        DB db = mongoContext.getDatabase();
        GridFS fs = new GridFS(db, FOLDER_NAME);
        fs.remove(new ObjectId(imageId));
    }

    private InputStream resize(ImageEnum imageType, InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Thumbnails.of(is).size(imageType.getWidth(), imageType.getHeight()).useOriginalFormat()
                .outputQuality(RESIZE_QUALITY).toOutputStream(os);
        return new ByteArrayInputStream(os.toByteArray());
    }

}
