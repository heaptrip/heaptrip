package com.heaptrip.service.content.trip;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.service.album.AlbumImageEnum;
import com.heaptrip.domain.service.content.trip.TripRouteService;
import com.heaptrip.service.album.AlbumServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class TripRouteServiceImpl extends AlbumServiceImpl implements TripRouteService {

    @Override
    public AlbumImage addAlbumImage(String routeId, String ownerId, String fileName, InputStream is) throws IOException {
        return addAlbumImage(routeId, ownerId, AlbumImageEnum.TRIP_ROUTE_IMAGE, fileName, is);
    }
}
