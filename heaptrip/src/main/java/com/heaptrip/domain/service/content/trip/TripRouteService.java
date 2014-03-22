package com.heaptrip.domain.service.content.trip;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.service.album.AlbumService;

import java.io.IOException;
import java.io.InputStream;

/**
 * Service to work with route of the trip
 */
public interface TripRouteService extends AlbumService {

    /**
     * Add image to route
     *
     * @param routeId  route id
     * @param ownerId  owner id
     * @param fileName file name
     * @param is       input stream
     * @return image
     * @throws IOException
     */
    public AlbumImage addAlbumImage(String routeId, String ownerId, String fileName, InputStream is) throws IOException;

}
