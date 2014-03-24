package com.heaptrip.service.image;

import com.heaptrip.domain.entity.image.FileReferences;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.entity.image.ImageSize;
import com.heaptrip.domain.repository.image.ImageRepository;
import com.heaptrip.domain.service.image.GridFileService;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.util.stream.StreamUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service(ImageService.SERVICE_NAME)
public class ImageServiceImpl implements ImageService {

    private static float RESIZE_QUALITY = 0.9f;

    @Autowired
    private GridFileService gridFileService;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image addImage(String targetId, String ownerId, ImageEnum imageType, String fileName, InputStream is) throws IOException {
        Assert.notNull(targetId, "targetId must not be null");
        Assert.notNull(ownerId, "ownerId must not be null");
        Assert.notNull(imageType, "imageType of album image must not be null");
        Assert.notNull(fileName, "fileName must not be null");
        Assert.notNull(is, "input stream must not be null");

        FileReferences refs = saveImage(fileName, imageType, is);

        Image image = new Image();
        image.setTarget(targetId);
        image.setName(fileName);
        image.setOwnerId(ownerId);
        image.setUploaded(new Date());
        image.setRefs(refs);

        return imageRepository.save(image);
    }

    @Override
    public Image addImage(ImageEnum imageType, String fileName, InputStream is) throws IOException {
        Assert.notNull(imageType, "imageType of album image must not be null");
        Assert.notNull(fileName, "fileName must not be null");
        Assert.notNull(is, "input stream must not be null");

        FileReferences refs = saveImage(fileName, imageType, is);

        Image image = new Image();
        image.setName(fileName);
        image.setUploaded(new Date());
        image.setRefs(refs);

        return imageRepository.save(image);
    }

    private FileReferences saveImage(String fileName, ImageEnum imageType, InputStream is) throws IOException {
        FileReferences result = new FileReferences();

        is = StreamUtils.getResetableInputStream(is);

        if (imageType.getSmallSize() != null) {
            result.setSmall(gridFileService.saveFile(fileName, getResizedInputStream(is, imageType.getSmallSize())));
            is.reset();
        }
        if (imageType.getMediumSize() != null) {
            result.setMedium(gridFileService.saveFile(fileName, getResizedInputStream(is, imageType.getMediumSize())));
            is.reset();
        }
        if (imageType.getFullSize() != null) {
            result.setFull(gridFileService.saveFile(fileName, getResizedInputStream(is, imageType.getFullSize())));
        }

        return result;
    }

    private InputStream getResizedInputStream(InputStream is, ImageSize imageSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Thumbnails.of(is).size(imageSize.getWidth(), imageSize.getHeight()).useOriginalFormat()
                .outputQuality(RESIZE_QUALITY).toOutputStream(os);
        return new ByteArrayInputStream(os.toByteArray());
    }

    @Override
    public Image getImageById(String imageId) {
        Assert.notNull(imageId, "imageId must not be null");
        return imageRepository.findOne(imageId);
    }

    @Override
    public List<Image> getImagesByTargetId(String targetId) {
        Assert.notNull(targetId, "targetId must not be null");
        return imageRepository.findByTargetId(targetId);
    }

    @Override
    public List<Image> getImagesByTargetId(String targetId, int skip, int limit) {
        Assert.notNull(targetId, "targetId must not be null");
        return imageRepository.findByTargetId(targetId, skip, limit);
    }

    @Override
    public long getCountByTargetId(String targetId) {
        // TODO konovalov
        return 0;
    }

    @Override
    public void updateImage(Image image) {
        Assert.notNull(image, "image must not be null");
        Assert.notNull(image.getId(), "image.id must not be null");
        imageRepository.update(image);
    }

    @Override
    public void removeImageById(String imageId) {
        Assert.notNull(imageId, "imageId must not be null");
        imageRepository.remove(imageId);
        // TODO konovalov: remove from GridFS
    }

    @Override
    public void removeImagesByIds(List<String> imageIds) {
        Assert.notNull(imageIds, "imageIds must not be null");
        for (String imageId : imageIds) {
            imageRepository.remove(imageId);
            // TODO konovalov: remove from GridFS
        }
    }

    @Override
    public void removeImagesByTargetId(String targetId) {
        Assert.notNull(targetId, "targetId must not be null");
        imageRepository.removeByTargetId(targetId);
        // TODO konovalov: remove from GridFS
    }

    @Override
    public void like(String imageId) {
        Assert.notNull(imageId, "imageId must not be null");
        imageRepository.incLike(imageId);
    }
}
