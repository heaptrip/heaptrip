package com.forrest.repository.post;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heaptrip.domain.entity.post.PostEntity;
import com.heaptrip.domain.repository.post.PostRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
@Ignore
public class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;

	// @Test
	public void testMongoRead() {
		List<PostEntity> postList = postRepository.findAll();
		System.out.println("post size:" + postList.size());
		for (PostEntity post : postList) {
			System.out.println(post);
		}
	}

	// @Test
	public void testSolrGeoSearch() throws SolrServerException, MalformedURLException {
		SolrServer lbHttpSolrServer = new LBHttpSolrServer("http://heaptrip:Qazwsx321@1.solr.heaptrip.com/geo",
				"http://heaptrip:Qazwsx321@2.solr.heaptrip.com/geo");

		String strQquery = "Ижевск";
		SolrQuery parameters = new SolrQuery();
		parameters.setParam("collection", "geo");
		parameters.set("q", strQquery);
		parameters.set("start", "0");
		parameters.set("rows", "10");
		parameters.set("defType", "dismax");
		parameters.set("qf", "text_ru text_en");

		QueryResponse response = lbHttpSolrServer.query(parameters);
		SolrDocumentList results = response.getResults();
		System.out.println("size=" + results.size());
		for (int i = 0; i < results.size(); ++i) {
			System.out.println(results.get(i));
		}
	}

	// @Test
	public void testSolrIndex() throws SolrServerException, IOException {
		String endpoints[] = { "http://heaptrip:Qazwsx321@1.solr.heaptrip.com/content",
				"http://heaptrip:Qazwsx321@2.solr.heaptrip.com/content",
				"http://heaptrip:Qazwsx321@1.solr.heaptrip.com/geo",
				"http://heaptrip:Qazwsx321@2.solr.heaptrip.com/geo" };
		String zookeeperEndpoints = "5.61.36.145:2181,5.61.32.48:2181,5.61.41.73:2181";
		String collectionName = "content";

		LBHttpSolrServer lbSolrServer = new LBHttpSolrServer(endpoints);
		CloudSolrServer cloudSolrServer = new CloudSolrServer(zookeeperEndpoints, lbSolrServer);
		cloudSolrServer.setDefaultCollection(collectionName);

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", UUID.randomUUID().toString());
		doc.addField("text_en", "hello");
		doc.addField("text_ru", "Привет");

		UpdateRequest update2 = new UpdateRequest();
		update2.setParam("collection", "content");
		update2.add(doc);
		update2.process(cloudSolrServer);
	}

	// @Test
	public void testSolrUpdate() throws SolrServerException, IOException {
		SolrServer server = new HttpSolrServer("http://heaptrip:Qazwsx321@1.solr.heaptrip.com/content");

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
