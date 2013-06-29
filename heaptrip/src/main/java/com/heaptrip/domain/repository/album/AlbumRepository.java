package com.heaptrip.domain.repository.album;

import java.util.List;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<AlbumImage> {

	public List<AlbumImage> findByTargetId(String targetId);

	public List<AlbumImage> findByTargetId(String targetId, int limit);

	public void updateAlbumImage(AlbumImage albumImage);

	public void incLike(String albumImageId);

	public void removeByTargetId(String targetId);
}
