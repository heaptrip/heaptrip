package com.heaptrip.web.controller.content.post;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.feed.ContentFeedService;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.modelservice.CommentModelService;
import com.heaptrip.web.modelservice.ContentModelService;
import com.heaptrip.web.modelservice.CountersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PostController extends ExceptionHandlerControler {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    @Qualifier(ContentModelService.SERVICE_NAME)
    private ContentModelService contentModelService;

    @Autowired
    private ContentFeedService contentFeedService;

    @Autowired
    private CommentModelService commentModelService;

    @Autowired
    private CountersService countersService;

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
            List<ContentModel> contentModels = contentModelService.getContentModelsByCriteria(feedCriteria);
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
            postModel = contentModelService.getContentModelByContentId(postId, ContentEnum.POST);
            mv.addObject("comments", commentModelService.getComments(postId));
        }
        return mv.addObject("post", postModel);

    }
}
