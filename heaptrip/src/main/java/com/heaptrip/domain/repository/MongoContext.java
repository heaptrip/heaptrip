package com.heaptrip.domain.repository;

import com.mongodb.DB;
import com.mongodb.DBCollection;

public interface MongoContext {
	public static final String SERVICE_NAME = "mongoContext";

	public DB getDb();
	
	public DBCollection getDbCollection(String name);
}
