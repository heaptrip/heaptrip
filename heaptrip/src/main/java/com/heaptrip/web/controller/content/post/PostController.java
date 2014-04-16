package com.heaptrip.web.controller.content.post;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.ContentFeedService;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.post.PostModel;
import com.heaptrip.web.modelservice.CommentModelService;
import com.heaptrip.web.modelservice.CountersService;
import com.heaptrip.web.modelservice.PostModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PostController extends ExceptionHandlerControler {

    @Autowired
    private ContentFeedService contentFeedService;

    @Autowired
    private CommentModelService commentModelService;

    @Autowired
    private CountersService countersService;

    @Autowired
    private PostModelService postModelService;

    @RequestMapping(value = "posts", method = RequestMethod.GET)
    public String getPosts() {
        return "posts";
    }

    @RequestMapping(value = "posts", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getPostsByCriteria(@RequestBody FeedCriteria feedCriteria) {
        try {
            Map<String, Object> result = new HashMap();
            feedCriteria.setContentType(ContentEnum.POST);
            List<PostModel> contentModels = postModelService.getPostModelsByCriteria(feedCriteria);
            result.put("posts", contentModels);
            result.put("count", contentFeedService.getCountByFeedCriteria(feedCriteria));
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }

    }

    @RequestMapping(value = "post", method = RequestMethod.GET)
    public ModelAndView getPost(@RequestParam(value = "id", required = false) String postId) {
        ModelAndView mv = new ModelAndView();
        ContentModel postModel = null;
        if (postId != null) {
            countersService.incViews(postId);
            postModel = postModelService.getPostModelBytId(postId);
            mv.addObject("comments", commentModelService.getComments(postId));
        }
        return mv.addObject("post", postModel);

    }

    @RequestMapping(value = "post_modify_info", method = RequestMethod.GET)
    public ModelAndView getPostEdit(@RequestParam(value = "id", required = false) String postId) {
        ModelAndView mv = new ModelAndView();
        ContentModel postModel = null;
        if (postId != null) {
            countersService.incViews(postId);
            postModel = postModelService.getPostModelBytId(postId);
            mv.addObject("comments", commentModelService.getComments(postId));
        }
        return mv.addObject("post", postModel);

    }

    @RequestMapping(value = "security/post_save", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> savePost(@RequestBody PostModel postModel) {
        try {
            postModelService.savePostModel(postModel);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "security/post_update", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> updateTripInfo(@RequestBody PostModel postModel) {
        try {
            postModelService.updatePostModel(postModel);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }
}
