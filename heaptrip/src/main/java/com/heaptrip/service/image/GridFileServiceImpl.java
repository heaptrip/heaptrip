package com.heaptrip.service.image;

import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.domain.service.image.GridFileService;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class GridFileServiceImpl implements GridFileService {

    private static final String FOLDER_NAME = "images";

    @Autowired
    private MongoContext mongoContext;

    @Override
    public String saveFile(String fileName, InputStream is) throws IOException {
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
    public InputStream getFile(String fileId) {
        Assert.notNull(fileId, "fileId must not be null");
        DB db = mongoContext.getDatabase();
        GridFS fs = new GridFS(db, FOLDER_NAME);
        GridFSDBFile file = fs.find(new ObjectId(fileId));
        return (file == null) ? null : file.getInputStream();
    }

    @Override
    public void removeFile(String fileId) {
        Assert.notNull(fileId, "fileId must not be null");
        DB db = mongoContext.getDatabase();
        GridFS fs = new GridFS(db, FOLDER_NAME);
        fs.remove(new ObjectId(fileId));
    }


    @Override
    public void removeFiles(Collection fileIds) {
        Assert.notEmpty(fileIds, "fileIds must not be empty");
        Set<ObjectId> objectIds = new HashSet<>(fileIds.size());
        for (Object fileId : fileIds) {
            objectIds.add(new ObjectId((String) fileId));
        }
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new BasicDBObject("$in", objectIds));
        DB db = mongoContext.getDatabase();
        GridFS fs = new GridFS(db, FOLDER_NAME);
        fs.remove(query);
    }
}
