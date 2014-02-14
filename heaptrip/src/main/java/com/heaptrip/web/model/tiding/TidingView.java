package com.heaptrip.web.model.tiding;

import java.util.Date;

@Deprecated
public interface TidingView {

	String getName();

	void setName(String name);

	Date getDateCreate();
	
	void setDateCreate(Date dateCreate);

}
