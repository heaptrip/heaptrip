package com.heaptrip.web.controller.post;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heaptrip.domain.entity.post.ImageEntity;
import com.heaptrip.domain.entity.post.PostEntity;
import com.heaptrip.domain.repository.post.PostRepository;
import com.heaptrip.web.model.post.PostImage;
import com.heaptrip.web.model.post.PostView;

@Controller
public class PostController {

	private static final Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostRepository postRepository;

	@RequestMapping(value = "posts", method = RequestMethod.GET)
	public @ModelAttribute("posts")
	Collection<PostView> getPostsPage() {
		Collection<PostView> result = new ArrayList<PostView>();
		List<PostEntity> postList = postRepository.findAll();
		if (postList != null) {
			for (PostEntity post : postList) {
				PostView view = PostConverter.postEntityToView(post);
				result.add(view);
			}
		}
		return result;
	}

	@RequestMapping(value = "post", method = RequestMethod.GET)
	public @ModelAttribute("post")
	PostView getPostPage(@RequestParam String id) {
		PostEntity postEntity = postRepository.findById(id);
		PostView view = new PostView();
		if (postEntity != null) {
			view = PostConverter.postEntityToView(postEntity);
		}
		return view;
	}

	@RequestMapping(value = "post/edit", method = RequestMethod.GET)
	public @ModelAttribute("post")
	PostView getPostEditPage(@RequestParam(required = false) String id) {
		PostView view = new PostView();
		if (id != null) {
			PostEntity postEntity = postRepository.findById(id);
			if (postEntity != null) {
				view = PostConverter.postEntityToView(postEntity);
			}
		}
		return view;
	}

	@RequestMapping(value = "/post/edit", method = RequestMethod.POST)
	public @ResponseBody
	void savePost(@RequestBody PostView view) {
		PostEntity postEntity = PostConverter.postViewToEntity(view);
		postEntity.setDateCreate(new Date());
		postRepository.savePost(postEntity);
	}

	@RequestMapping(value = "post/upload/header", method = RequestMethod.POST)
	public @ResponseBody
	List<PostImage> uploadHeader(MultipartHttpServletRequest multipart) throws IOException {
		List<PostImage> uploadedImages = new ArrayList<PostImage>();
		Map<String, MultipartFile> files = multipart.getFileMap();
		for (MultipartFile file : files.values()) {
			logger.info("Received image: {}, size {}", file.getOriginalFilename(), file.getSize());

			// 100x100
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			Long start = System.currentTimeMillis();
			Thumbnails.of(file.getInputStream()).size(100, 100).outputFormat("png").toOutputStream(os);
			System.out.println("100x100 resize=" + (System.currentTimeMillis() - start));

			start = System.currentTimeMillis();
			ImageEntity image = postRepository.saveImage(new ByteArrayInputStream(os.toByteArray()),
					file.getOriginalFilename());
			System.out.println("100x100 saveImage=" + (System.currentTimeMillis() - start));

			PostImage uf = new PostImage();
			uf.setId(image.getId());
			uf.setName(image.getName());
			uf.setSize(image.getSize());
			uploadedImages.add(uf);

			// 200x200
			os = new ByteArrayOutputStream();
			start = System.currentTimeMillis();
			Thumbnails.of(file.getInputStream()).size(200, 200).outputFormat("png").toOutputStream(os);
			System.out.println("200x200 resize=" + (System.currentTimeMillis() - start));

			start = System.currentTimeMillis();
			image = postRepository.saveImage(new ByteArrayInputStream(os.toByteArray()), file.getOriginalFilename());
			System.out.println("200x200 saveImage=" + (System.currentTimeMillis() - start));

			uf = new PostImage();
			uf.setId(image.getId());
			uf.setName(image.getName());
			uf.setSize(image.getSize());
			uploadedImages.add(uf);

			// 843x403
			os = new ByteArrayOutputStream();
			start = System.currentTimeMillis();
			Thumbnails.of(file.getInputStream()).size(843, 403).outputFormat("png").toOutputStream(os);
			System.out.println("843x403 resize=" + (System.currentTimeMillis() - start));

			start = System.currentTimeMillis();
			image = postRepository.saveImage(new ByteArrayInputStream(os.toByteArray()), file.getOriginalFilename());
			System.out.println("843x403 saveImage=" + (System.currentTimeMillis() - start));

			uf = new PostImage();
			uf.setId(image.getId());
			uf.setName(image.getName());
			uf.setSize(image.getSize());
			uploadedImages.add(uf);

			// 1000x1000
			os = new ByteArrayOutputStream();
			start = System.currentTimeMillis();
			Thumbnails.of(file.getInputStream()).size(1000, 1000).outputFormat("png").toOutputStream(os);
			System.out.println("1000x1000 resize=" + (System.currentTimeMillis() - start));

			start = System.currentTimeMillis();
			image = postRepository.saveImage(new ByteArrayInputStream(os.toByteArray()), file.getOriginalFilename());
			System.out.println("1000x1000 saveImage=" + (System.currentTimeMillis() - start));

			uf = new PostImage();
			uf.setId(image.getId());
			uf.setName(image.getName());
			uf.setSize(image.getSize());
			uploadedImages.add(uf);
		}
		return uploadedImages;
	}

	@RequestMapping(value = "post/upload", method = RequestMethod.POST)
	public String uploadImage(MultipartHttpServletRequest multipart, RedirectAttributes redirect,
			@RequestParam(value = "CKEditor") String ckeInstance,
			@RequestParam(value = "CKEditorFuncNum") String ckeFuncNum) throws IOException {
		Map<String, MultipartFile> files = multipart.getFileMap();
		ImageEntity image = null;
		for (MultipartFile file : files.values()) {
			logger.info("Received image: {}, size {}", file.getOriginalFilename(), file.getSize());
			image = postRepository.saveImage(file.getInputStream(), file.getOriginalFilename());
			break;
		}

		return String.format("redirect:/postImage.html?CKEditor=%s&CKEditorFuncNum=%s&imageId=%s", ckeInstance,
				ckeFuncNum, image.getId());
	}

	@RequestMapping("/image.html")
	public ResponseEntity<byte[]> getImage(@RequestParam(value = "imageId") String imageId) throws IOException {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		InputStream in = postRepository.getImage(imageId);
		return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "postImage", method = RequestMethod.GET)
	public void getPostImagePage() {
		// TODO remove this page&method
	}

	// TODO post
	@RequestMapping(value = "post/remove", method = RequestMethod.GET)
	public String removePost(@RequestParam(value = "id") String id) {
		postRepository.removeById(id);
		return "redirect:/posts.html";
	}
}
