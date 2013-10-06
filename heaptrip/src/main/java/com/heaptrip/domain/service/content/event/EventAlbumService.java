package com.heaptrip.domain.service.content.event;

import java.io.IOException;
import java.io.InputStream;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.service.album.AlbumService;

/**
 * 
 * Service for working with images in events
 * 
 */
public interface EventAlbumService extends AlbumService {

	/**
	 * Add image to event
	 * 
	 * @param eventId
	 *            event id
	 * @param ownerId
	 *            owner id
	 * @param fileName
	 *            file name
	 * @param is
	 *            input stream
	 * @return image
	 * @throws IOException
	 */
	public AlbumImage addAlbumImage(String eventId, String ownerId, String fileName, InputStream is) throws IOException;

}
