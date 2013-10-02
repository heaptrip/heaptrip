package com.heaptrip.service.content.trip;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.repository.content.trip.TripRepository;
import com.heaptrip.domain.service.album.AlbumImageEnum;
import com.heaptrip.domain.service.content.trip.TripRouteService;
import com.heaptrip.service.album.AlbumServiceImpl;

@Service
public class TripRouteServiceImpl extends AlbumServiceImpl implements TripRouteService {

	@Autowired
	private TripRepository tripRepository;

	@Override
	public AlbumImage addRouteImage(String routeId, String ownerId, String fileName, InputStream is) throws IOException {
		return addAlbumImage(routeId, ownerId, AlbumImageEnum.TRIP_ROUTE_IMAGE, fileName, is);
	}

	@Override
	public List<AlbumImage> getRouteImages(String routeId) {
		return getAlbumImagesByTargetId(routeId);
	}

	@Override
	public List<AlbumImage> getRouteImages(String routeId, int limit) {
		return getAlbumImagesByTargetId(routeId, limit);
	}

}
