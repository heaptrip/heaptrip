package com.heaptrip.web.modelservice;

import java.util.Date;

import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.web.model.content.DateModel;
import com.heaptrip.web.model.content.ImageModel;

public interface BaseModelTypeConverterService {

	DateModel convertDate(Date date);

	ImageModel convertImage(Image image);

}