package com.heaptrip.domain.repository;

import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.DBCollection;

public interface MongoContext {

	public DB getDatabase();

	public MongoCollection getCollection(String name);

	@Deprecated
	public DB getDb();

	@Deprecated
	public DBCollection getDbCollection(String name);
}
