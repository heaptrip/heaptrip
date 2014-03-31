package com.heaptrip.web.controller.content;

import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.content.CommentModel;
import com.heaptrip.web.model.content.ContentRatingModel;
import com.heaptrip.web.modelservice.CommentModelService;
import com.heaptrip.web.modelservice.CountersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author voronenko
 */
@Controller
public class ContentController extends ExceptionHandlerControler {

    private static final Logger LOG = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    private CommentModelService commentModelService;

    @Autowired
    private CountersService countersService;

    @RequestMapping(value = "security/comment_save", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> saveComment(@RequestBody CommentModel commentModel) {
        try {
            commentModelService.saveComment(commentModel);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "security/add_content_rating", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> addContentRating(@RequestBody ContentRatingModel ratingModel) {
        try {
            return Ajax.successResponse(countersService.addContentRating(ratingModel));
        } catch (Throwable e) {
            throw new RestException(e);
        }

    }
}
