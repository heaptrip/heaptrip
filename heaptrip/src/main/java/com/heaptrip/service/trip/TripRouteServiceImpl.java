package com.heaptrip.service.trip;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.entity.trip.Route;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.heaptrip.domain.service.album.AlbumImageEnum;
import com.heaptrip.domain.service.trip.TripRouteService;
import com.heaptrip.service.album.AlbumServiceImpl;
import com.heaptrip.util.LanguageUtils;

@Service
public class TripRouteServiceImpl extends AlbumServiceImpl implements TripRouteService {

	@Autowired
	private TripRepository tripRepository;

	@Override
	public Route getRoute(String tripId, Locale locale) {
		Assert.notNull(tripId, "tripId must not be null");
		Assert.notNull(locale, "locale must not be null");
		Trip trip = tripRepository.getRoute(tripId, locale);
		if (trip == null) {
			throw new IllegalArgumentException(String.format("trip with id=%s is not found", tripId));
		}
		return trip.getRoute();
	}

	@Override
	public void updateRoute(String tripId, Route route, Locale locale) {
		Assert.notNull(tripId, "tripId must not be null");
		Assert.notNull(route, "route must not be null");
		Assert.notNull(locale, "locale must not be null");
		Trip trip = tripRepository.getMainLanguage(tripId);
		if (trip == null) {
			throw new IllegalArgumentException(String.format("trip with id=%s is not found", tripId));
		}
		String mainLang = trip.getMainLang();
		if (mainLang == null) {
			throw new IllegalStateException(String.format("trip with id=%s does not have a main language", tripId));
		}
		String lang = LanguageUtils.getLanguageByLocale(locale);
		if (mainLang.equals(lang)) {
			route.getText().setMainLanguage(mainLang);
		}
		trip.setRoute(route);
		tripRepository.updateRoute(trip, locale);
	}

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
