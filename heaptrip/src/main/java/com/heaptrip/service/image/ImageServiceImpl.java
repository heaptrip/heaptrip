package com.heaptrip.service.image;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.image.FileReferences;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.entity.image.ImageSize;
import com.heaptrip.domain.repository.image.ImageRepository;
import com.heaptrip.domain.service.image.GridFileService;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.util.stream.StreamUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ImageServiceImpl implements ImageService {

    private static float RESIZE_QUALITY = 0.9f;

    @Autowired
    private GridFileService gridFileService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    @Qualifier("requestScopeService")
    private RequestScopeService requestScopeService;

    @Override
    public Image addImage(String targetId, ImageEnum imageType, String fileName, InputStream is) throws IOException {
        Assert.notNull(targetId, "targetId must not be null");
        Assert.notNull(imageType, "imageType of album image must not be null");
        Assert.notNull(fileName, "fileName must not be null");
        Assert.notNull(is, "input stream must not be null");

        FileReferences refs = saveImage(fileName, imageType, is);
        User owner = requestScopeService.getCurrentUser();

        Image image = new Image();
        image.setTarget(targetId);
        image.setName(fileName);
        if (owner != null) {
            image.setOwnerId(owner.getId());
        }
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
        User owner = requestScopeService.getCurrentUser();

        Image image = new Image();
        image.setName(fileName);
        if (owner != null) {
            image.setOwnerId(owner.getId());
        }
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
        Assert.notNull(targetId, "targetId must not be null");
        return imageRepository.getCountByTargetId(targetId);
    }

    @Override
    public void updateImageNameAndText(String imageId, String name, String text) {
        Assert.notNull(imageId, "imageId must not be null");
        imageRepository.updateNameAndText(imageId, name, text);
    }

    @Override
    public void removeImageById(String imageId) {
        Assert.notNull(imageId, "imageId must not be null");

        Image image = imageRepository.findOne(imageId);
        Assert.notNull(image, "image with id:" + imageId + " dose not exist");

        Set<String> fileIds = new HashSet<>();
        if (image.getRefs() != null) {
            if (StringUtils.isNotEmpty(image.getRefs().getSmall())) {
                fileIds.add(image.getRefs().getSmall());
            }
            if (StringUtils.isNotEmpty(image.getRefs().getMedium())) {
                fileIds.add(image.getRefs().getMedium());
            }
            if (StringUtils.isNotEmpty(image.getRefs().getFull())) {
                fileIds.add(image.getRefs().getFull());
            }
        }

        if (!fileIds.isEmpty()) {
            gridFileService.removeFiles(fileIds);
        }
        imageRepository.remove(imageId);
    }

    @Override
    public void removeImagesByIds(List<String> imageIds) {
        Assert.notNull(imageIds, "imageIds must not be null");
        Iterable<Image> images = imageRepository.findAll(imageIds);
        if (images != null) {
            Set<String> fileIds = new HashSet<>();
            for (Image image : images) {
                if (image.getRefs() != null) {
                    if (StringUtils.isNotEmpty(image.getRefs().getSmall())) {
                        fileIds.add(image.getRefs().getSmall());
                    }
                    if (StringUtils.isNotEmpty(image.getRefs().getMedium())) {
                        fileIds.add(image.getRefs().getMedium());
                    }
                    if (StringUtils.isNotEmpty(image.getRefs().getFull())) {
                        fileIds.add(image.getRefs().getFull());
                    }
                }
            }
            if (!fileIds.isEmpty()) {
                gridFileService.removeFiles(fileIds);
            }
            imageRepository.remove(imageIds);
        }
    }

    @Override
    public void removeImagesByTargetId(String targetId) {
        Assert.notNull(targetId, "targetId must not be null");
        List<Image> images = imageRepository.findByTargetId(targetId);
        if (images != null) {
            Set<String> fileIds = new HashSet<>();
            for (Image image : images) {
                if (image.getRefs() != null) {
                    if (StringUtils.isNotEmpty(image.getRefs().getSmall())) {
                        fileIds.add(image.getRefs().getSmall());
                    }
                    if (StringUtils.isNotEmpty(image.getRefs().getMedium())) {
                        fileIds.add(image.getRefs().getMedium());
                    }
                    if (StringUtils.isNotEmpty(image.getRefs().getFull())) {
                        fileIds.add(image.getRefs().getFull());
                    }
                }
            }
            if (!fileIds.isEmpty()) {
                gridFileService.removeFiles(fileIds);
            }
            imageRepository.removeByTargetId(targetId);
        }
    }

    @Override
    public void like(String imageId) {
        Assert.notNull(imageId, "imageId must not be null");
        imageRepository.incLike(imageId);
    }
}
