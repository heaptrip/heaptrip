package com.heaptrip.domain.repository;

import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.DBCollection;

public interface MongoContext {
	public static final String SERVICE_NAME = "mongoContext";

	@Deprecated
	public DB getDb();

	@Deprecated
	public DBCollection getDbCollection(String name);

	public MongoCollection getCollection(String name);
}
