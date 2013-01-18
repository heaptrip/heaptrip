package com.forrest.repository.post;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
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

	@Test
	public void testMongoRead() {
		List<PostEntity> postList = postRepository.findAll();
		System.out.println("post size:" + postList.size());
		for (PostEntity post : postList) {
			System.out.println(post);
		}
	}
}
