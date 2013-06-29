package com.heaptrip.service.album;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.entity.album.ImageOwner;
import com.heaptrip.domain.entity.album.ImageReferences;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.repository.album.AlbumRepository;
import com.heaptrip.domain.service.album.AlbumImageEnum;
import com.heaptrip.domain.service.album.AlbumService;
import com.heaptrip.domain.service.image.ImageService;

@Service(AlbumService.SERVICE_NAME)
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private ImageService imageService;

	@Override
	public AlbumImage addAlbumImage(String targetId, String ownerId, AlbumImageEnum type, String fileName,
			InputStream is) throws IOException {
		Assert.notNull(targetId, "targetId must not be null");
		Assert.notNull(ownerId, "ownerId must not be null");
		Assert.notNull(type, "type of album image must not be null");
		Assert.notNull(fileName, "fileName must not be null");
		Assert.notNull(is, "input stream must not be null");

		ImageOwner owner = new ImageOwner();
		owner.setId(ownerId);
		// TODO konovalov set owner name
		// owner.setName(name)

		ImageReferences refs = saveImage(fileName, type, is);

		AlbumImage albumImage = new AlbumImage();
		albumImage.setTarget(targetId);
		albumImage.setName(fileName);
		albumImage.setOwner(owner);
		albumImage.setUploaded(new Date());
		albumImage.setRefs(refs);

		return albumRepository.save(albumImage);
	}

	private ImageReferences saveImage(String fileName, AlbumImageEnum type, InputStream is) throws IOException {
		ImageReferences result = new ImageReferences();

		if (!is.markSupported()) {
			is = getResetableInputStream(is);
		}

		switch (type) {
		case TRIP_ALBUM_IMAGE:
			result.setSmall(imageService.saveImage(fileName, ImageEnum.TRIP_ALBUM_SMALL_IMAGE, is));
			is.reset();
			result.setMedium(imageService.saveImage(fileName, ImageEnum.TRIP_ALBUM_MEDIUM_IMAGE, is));
			is.reset();
			result.setFull(imageService.saveImage(fileName, ImageEnum.TRIP_ALBUM_FULL_IMAGE, is));
			break;
		case TRIP_ROUTE_IMAGE:
			result.setSmall(imageService.saveImage(fileName, ImageEnum.TRIP_ROUTE_SMALL_IMAGE, is));
			is.reset();
			result.setMedium(imageService.saveImage(fileName, ImageEnum.TRIP_ROUTE_MEDIUM_IMAGE, is));
			is.reset();
			result.setFull(imageService.saveImage(fileName, ImageEnum.TRIP_ROUTE_FULL_IMAGE, is));
			break;
		}

		return result;
	}

	private InputStream getResetableInputStream(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(is, baos);
		byte[] bytes = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return bais;
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
	public void removeAlbumImagesByTargetId(String targetId) {
		Assert.notNull(targetId, "targetId must not be null");
		albumRepository.removeByTargetId(targetId);
	}

	@Override
	public List<AlbumImage> getAlbumImagesByTargetId(String targetId) {
		Assert.notNull(targetId, "targetId must not be null");
		return albumRepository.findByTargetId(targetId);
	}

	@Override
	public List<AlbumImage> getAlbumImagesByTargetId(String targetId, int limit) {
		Assert.notNull(targetId, "targetId must not be null");
		return albumRepository.findByTargetId(targetId, limit);
	}

	@Override
	public AlbumImage getAlbumImageById(String albumImageId) {
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
