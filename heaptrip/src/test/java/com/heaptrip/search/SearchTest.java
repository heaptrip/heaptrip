package com.heaptrip.search;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heaptrip.domain.search.geo.GeoSearch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/META-INF/spring/test-context.xml")
@Ignore
public class SearchTest {
	private static String CONTENT_COLLECTION = "content";
	private static String GEO_COLLECTION = "geo";

	@Autowired
	private GeoSearch geoSearch;

	// @Test
	public void TestCloudSearch() throws SolrServerException, MalformedURLException {
		System.out.println("TestCloudSearch");

		String zookeeperEndpoints = "5.61.36.145:2181,5.61.32.48:2181,5.61.41.73:2181";
		CloudSolrServer cloudSolrServer = new CloudSolrServer(zookeeperEndpoints);
		cloudSolrServer.setDefaultCollection(GEO_COLLECTION);

		String strQquery = "jap республика germany rus москва питер";
		SolrQuery parameters = new SolrQuery();
		// parameters.setParam("collection", "geo");
		parameters.set("q", strQquery);
		parameters.set("start", "0");
		parameters.set("rows", "100");
		parameters.set("defType", "dismax");
		parameters.set("qf", "text_ru text_en");

		QueryResponse response = cloudSolrServer.query(parameters);
		System.out.println("QTime=" + response.getQTime());
		SolrDocumentList results = response.getResults();
		System.out.println("size=" + results.size());
		for (int i = 0; i < results.size(); ++i) {
			System.out.println(results.get(i));
		}
	}

	// @Test
	public void TestCloudIndex() throws SolrServerException, IOException {
		System.out.println("TestCloudIndex");

		String zookeeperEndpoints = "5.61.36.145:2181,5.61.32.48:2181,5.61.41.73:2181";
		CloudSolrServer cloudSolrServer = new CloudSolrServer(zookeeperEndpoints);
		cloudSolrServer.setDefaultCollection(CONTENT_COLLECTION);

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", UUID.randomUUID().toString());
		doc.addField("text_en", "hello");
		doc.addField("text_ru", "Привет");

		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		docs.add(doc);

		cloudSolrServer.add(docs);
		cloudSolrServer.commit();

		/*
		 * String endpoints[] = {
		 * "http://heaptrip:Qazwsx321@1.solr.heaptrip.com/content",
		 * "http://heaptrip:Qazwsx321@2.solr.heaptrip.com/content",
		 * "http://heaptrip:Qazwsx321@1.solr.heaptrip.com/geo",
		 * "http://heaptrip:Qazwsx321@2.solr.heaptrip.com/geo" }; String
		 * zookeeperEndpoints =
		 * "5.61.36.145:2181,5.61.32.48:2181,5.61.41.73:2181"; String
		 * collectionName = "content";
		 * 
		 * LBHttpSolrServer lbSolrServer = new LBHttpSolrServer(endpoints);
		 * CloudSolrServer cloudSolrServer = new
		 * CloudSolrServer(zookeeperEndpoints, lbSolrServer);
		 * cloudSolrServer.setDefaultCollection(collectionName);
		 * 
		 * SolrInputDocument doc = new SolrInputDocument(); doc.addField("id",
		 * UUID.randomUUID().toString()); doc.addField("text_en", "hello");
		 * doc.addField("text_ru", "Привет");
		 * 
		 * UpdateRequest update2 = new UpdateRequest();
		 * update2.setParam("collection", "content"); update2.add(doc);
		 * update2.process(cloudSolrServer);
		 */
	}

	@Test
	public void TestSingleSearch() throws SolrServerException, IOException {
		System.out.println("TestSingleSearch");

		String strQquery = "jap республика germany rus москва питер";
		String[] str = geoSearch.searchIdsByName(strQquery, 0, 10);

		/*
		 * SolrServer server = new
		 * HttpSolrServer("http://heaptrip:Qazwsx321@solr.heaptrip.com/geo");
		 * String strQquery = "jap республика germany rus москва питер";
		 * SolrQuery parameters = new SolrQuery(); //
		 * parameters.setParam("collection", "geo"); parameters.set("q",
		 * strQquery); parameters.set("start", "0"); parameters.set("rows",
		 * "100"); parameters.set("defType", "dismax"); parameters.set("qf",
		 * "text_ru text_en");
		 * 
		 * QueryResponse response = server.query(parameters);
		 * System.out.println("QTime=" + response.getQTime()); SolrDocumentList
		 * results = response.getResults(); System.out.println("size=" +
		 * results.size()); for (int i = 0; i < results.size(); ++i) {
		 * System.out.println(results.get(i)); SolrDocument doc =
		 * results.get(i); String id = (String) doc.getFieldValue("id");
		 * System.out.println("id=" + id); }
		 */
	}

	// @Test
	public void TestSingleIndex() throws SolrServerException, IOException {
		System.out.println("TestSingleIndex");

		SolrServer server = new HttpSolrServer("http://heaptrip:Qazwsx321@solr.heaptrip.com/content");

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", UUID.randomUUID().toString());
		doc.addField("text_en", "hello");
		doc.addField("text_ru", "Привет");

		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		docs.add(doc);

		server.add(docs);
		server.commit();
	}
}
