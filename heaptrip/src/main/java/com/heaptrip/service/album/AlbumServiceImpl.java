package com.heaptrip.service.album;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.entity.album.ImageOwner;
import com.heaptrip.domain.entity.album.ImageReferences;
import com.heaptrip.domain.repository.album.AlbumRepository;
import com.heaptrip.domain.service.album.AlbumImageEnum;
import com.heaptrip.domain.service.album.AlbumService;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.util.stream.StreamUtils;

@Service(AlbumService.SERVICE_NAME)
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private ImageService imageService;

	@Override
	public AlbumImage addAlbumImage(String targetId, String ownerId, AlbumImageEnum imageType, String fileName,
			InputStream is) throws IOException {
		Assert.notNull(targetId, "targetId must not be null");
		Assert.notNull(ownerId, "ownerId must not be null");
		Assert.notNull(imageType, "imageType of album image must not be null");
		Assert.notNull(fileName, "fileName must not be null");
		Assert.notNull(is, "input stream must not be null");

		ImageOwner owner = new ImageOwner();
		owner.setId(ownerId);

		// TODO konovalov set owner name
		// owner.setName(name)

		ImageReferences refs = saveImage(fileName, imageType, is);

		AlbumImage albumImage = new AlbumImage();
		albumImage.setTarget(targetId);
		albumImage.setName(fileName);
		albumImage.setOwner(owner);
		albumImage.setUploaded(new Date());
		albumImage.setRefs(refs);

		return albumRepository.save(albumImage);
	}

	private ImageReferences saveImage(String fileName, AlbumImageEnum imageType, InputStream is) throws IOException {
		ImageReferences result = new ImageReferences();

		is = StreamUtils.getResetableInputStream(is);

		result.setSmall(imageService.saveImage(fileName, imageType.getSmallImage(), is));
		is.reset();
		result.setMedium(imageService.saveImage(fileName, imageType.getMediumImage(), is));
		is.reset();
		result.setFull(imageService.saveImage(fileName, imageType.getFullImage(), is));

		return result;
	}

	@Override
	public void removeAlbumImage(String albumImageId) {
		Assert.notNull(albumImageId, "albumImageId must not be null");
		albumRepository.remove(albumImageId);
	}

	@Override
	public void removeAlbumImages(List<String> albumImageIds) {
		Assert.notNull(albumImageIds, "albumImageIds must not be null");
		for (String albumImageId : albumImageIds) {
			albumRepository.remove(albumImageId);
		}
	}

	@Override
	public void removeAllAlbumImages(String targetId) {
		Assert.notNull(targetId, "targetId must not be null");
		albumRepository.removeByTargetId(targetId);
	}

	@Override
	public List<AlbumImage> getAlbumImages(String targetId) {
		Assert.notNull(targetId, "targetId must not be null");
		return albumRepository.findByTargetId(targetId);
	}

	@Override
	public List<AlbumImage> getAlbumImages(String targetId, int limit) {
		Assert.notNull(targetId, "targetId must not be null");
		return albumRepository.findByTargetId(targetId, limit);
	}

	@Override
	public AlbumImage getAlbumImage(String albumImageId) {
		Assert.notNull(albumImageId, "albumImageId must not be null");
		return albumRepository.findOne(albumImageId);
	}

	@Override
	public void updateAlbumImage(AlbumImage albumImage) {
		Assert.notNull(albumImage, "albumImage must not be null");
		Assert.notNull(albumImage.getId(), "albumImage.getId must not be null");
		albumRepository.updateAlbumImage(albumImage);
	}

	@Override
	public void like(String albumImageId) {
		Assert.notNull(albumImageId, "albumImageId must not be null");
		albumRepository.incLike(albumImageId);
	}
}
