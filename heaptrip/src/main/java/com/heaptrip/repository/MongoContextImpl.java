package com.heaptrip.repository;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.repository.MongoContext;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

@Service(MongoContext.SERVICE_NAME)
public class MongoContextImpl implements MongoContext {
	private static final Logger logger = LoggerFactory.getLogger(MongoContextImpl.class);

	private static final String ADMIN_DB_NAME = "admin";

	private static final int PORT = 27017;

	@Value("${database.urls}")
	private String dbUrls;

	@Value("${database.name}")
	private String dbName;

	@Value("${database.username}")
	private String username;

	@Value("${database.password}")
	private String password;

	private MongoClient mongoClient;

	@PostConstruct
	public void init() throws UnknownHostException {
		if (mongoClient == null) {
			logger.info("MongoClient initialization ...");
			logger.info("Database name: {}", dbName);
			String[] urls = dbUrls.split(",");
			if (urls != null && urls.length > 0 && !urls[0].isEmpty()) {
				List<ServerAddress> saList = new ArrayList<ServerAddress>();
				for (String url : urls) {
					String[] arr = url.split(":");
					String host = arr[0];
					int port = (arr.length > 1) ? Integer.parseInt(arr[1]) : PORT;
					saList.add(new ServerAddress(host, port));
					logger.info("Add mongodb server: {}:{}", host, port);
				}
				mongoClient = new MongoClient(saList);
				mongoClient.setWriteConcern(WriteConcern.SAFE);
				logger.info("Defoult WriteConcern.SAFE");

				DB db = mongoClient.getDB(ADMIN_DB_NAME);
				boolean auth = db.authenticate(username, password.toCharArray());
				if (auth) {
					logger.info("Successfully database authenticate by admin user with username: {}", username);
				} else {
					throw new MongoException("Error database authenticate by admin user with username " + username);
				}

				logger.info("MongoClient successfully initialized");
			} else {
				logger.error("MongoClient not initialized: databese urls not defined");
			}
		}
	}

	@PreDestroy
	public void release() {
		logger.info("close MongoClient");
		if (mongoClient != null) {
			mongoClient.close();
		}
	}

	@Override
	public DB getDb() {
		DB db = mongoClient.getDB(dbName);
		return db;
	}

	@Override
	public DBCollection getDbCollection(String name) {
		DB db = getDb();
		DBCollection coll = db.getCollection(name);
		return coll;
	}
}
