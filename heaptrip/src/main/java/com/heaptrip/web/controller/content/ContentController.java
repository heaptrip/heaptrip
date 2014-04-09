package com.heaptrip.web.controller.content;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.content.FavoriteContentService;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;
import com.heaptrip.domain.service.content.feed.ContentFeedService;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.content.CommentModel;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.content.ContentRatingModel;
import com.heaptrip.web.modelservice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author voronenko
 */
@Controller
public class ContentController extends ExceptionHandlerControler {

    @Autowired
    private CommentModelService commentModelService;

    @Autowired
    private CountersService countersService;

    @Autowired
    private ContentFeedService contentFeedService;

    @Autowired
    private TripModelService tripModelService;

    @Autowired
    private PostModelService postModelService;

    @Autowired
    @Qualifier(ContentModelService.SERVICE_NAME)
    private ContentModelService contentModelService;

    @Autowired
    private RequestScopeService requestScopeService;

    @Autowired
    private FavoriteContentService favoriteContentService;

    @RequestMapping(value = "add_favorite", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> addFavorite(@RequestBody String contentId) {
        try {
            User user = requestScopeService.getCurrentUser();
            favoriteContentService.addFavorites(contentId, user.getId());
            return Ajax.emptyResponse();
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }

    @RequestMapping(value = "news", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getContentsByFeedCriteria(@RequestBody FeedCriteria feedCriteria) {
        try {
            Map<String, Object> result = new HashMap();
            List<ContentModel> models = new ArrayList<>();
            feedCriteria.setLocale(requestScopeService.getCurrentLocale());
            List<Content> contents = contentFeedService.getContentsByFeedCriteria(feedCriteria);
            if (!CollectionUtils.isEmpty(contents)) {
                for (Content content : contents) {
                    models.add(convertContent(content));
                }
            }
            result.put("contents", models);
            result.put("count", contentFeedService.getCountByFeedCriteria(feedCriteria));
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }

    @RequestMapping(value = "my/news", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getContentsMyAccountCriteria(@RequestBody MyAccountCriteria myAccountCriteriaa) {
        try {
            Map<String, Object> result = new HashMap();
            List<ContentModel> models = new ArrayList<>();
            myAccountCriteriaa.setLocale(requestScopeService.getCurrentLocale());
            List<Content> contents = contentFeedService.getContentsByMyAccountCriteria(myAccountCriteriaa);
            if (!CollectionUtils.isEmpty(contents)) {
                for (Content content : contents) {
                    models.add(convertContent(content));
                }
            }
            result.put("contents", models);
            result.put("count", contentFeedService.getCountByMyAccountCriteria(myAccountCriteriaa));
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }

    @RequestMapping(value = "foreign/news", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getContentsByForeignAccountCriteria(@RequestBody ForeignAccountCriteria foreignAccountCriteria) {
        try {
            Map<String, Object> result = new HashMap();
            List<ContentModel> models = new ArrayList<>();
            foreignAccountCriteria.setLocale(requestScopeService.getCurrentLocale());
            List<Content> contents = contentFeedService.getContentsByForeignAccountCriteria(foreignAccountCriteria);
            if (!CollectionUtils.isEmpty(contents)) {
                for (Content content : contents) {
                    models.add(convertContent(content));
                }
            }
            result.put("contents", models);
            result.put("count", contentFeedService.getCountByForeignAccountCriteria(foreignAccountCriteria));
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }

    private ContentModel convertContent(Content content) {
        ContentModel model = null;
        if (content != null) {
            switch (content.getContentType()) {
                case TRIP:
                    model = tripModelService.convertTrip((Trip) content);
                    break;
                case POST:
                    model = postModelService.convertPost((Post) content);
                    break;
                default:
                    model = contentModelService.convertContent(content);
                    break;
            }
        }
        return model;
    }

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
