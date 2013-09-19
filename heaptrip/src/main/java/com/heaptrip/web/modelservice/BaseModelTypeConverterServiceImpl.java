package com.heaptrip.web.modelservice;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.service.system.RequestScopeServiceImpl;
import com.heaptrip.web.model.content.DateModel;
import com.heaptrip.web.model.content.ImageModel;

@Service
public class BaseModelTypeConverterServiceImpl extends RequestScopeServiceImpl implements BaseModelTypeConverterService {

	@Override
	public DateModel convertDate(Date date) {
		DateModel result = new DateModel();
		if (date != null) {
			result.setValue(date);
			result.setText(DateFormat.getDateInstance(DateFormat.SHORT, getCurrentLocale()).format(date));
		}
		return result;
	}

	@Override
	public ImageModel convertImage(Image image) {
		ImageModel result = null;
		if (image != null) {
			result = new ImageModel();
			result.setId(image.getId());
			result.setName(image.getName());
			result.setText(image.getText());
		}
		return result;
	}

}
