package com.heaptrip.service.content.trip;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.service.album.AlbumImageEnum;
import com.heaptrip.domain.service.content.trip.TripAlbumService;
import com.heaptrip.service.album.AlbumServiceImpl;

@Service
public class TripAlbumServiceImpl extends AlbumServiceImpl implements TripAlbumService {

	@Override
	public AlbumImage addOwnerAlbumImage(String tripId, String ownerId, String fileName, InputStream is)
			throws IOException {
		return addAlbumImage(tripId, ownerId, AlbumImageEnum.TRIP_ALBUM_IMAGE, fileName, is);
	}

	@Override
	public AlbumImage addTableAlbumImage(String tableId, String memberId, String fileName, InputStream is)
			throws IOException {
		return addAlbumImage(tableId, memberId, AlbumImageEnum.TRIP_ALBUM_IMAGE, fileName, is);
	}

	@Override
	public List<AlbumImage> getOwnerAlbumImages(String tripId) {
		return getAlbumImages(tripId);
	}

	@Override
	public List<AlbumImage> getOwnerAlbumImages(String tripId, int limit) {
		return getAlbumImages(tripId, limit);
	}

	@Override
	public List<AlbumImage> getTableAlbumImages(String tableId) {
		return getAlbumImages(tableId);
	}

	@Override
	public List<AlbumImage> getTableAlbumImages(String tableId, int limit) {
		return getAlbumImages(tableId, limit);
	}

}
