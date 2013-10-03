package com.heaptrip.web.controller.content;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.content.CommentModel;
import com.heaptrip.web.modelservice.CommentModelService;

/**
 * 
 * @author voronenko
 * 
 */
@Controller
public class ContentController extends ExceptionHandlerControler {

	private static final Logger LOG = LoggerFactory.getLogger(ContentController.class);

	@Autowired
	@Qualifier("requestScopeService")
	private RequestScopeService scopeService;

	@Autowired
	private CommentModelService commentModelService;

	@RequestMapping(value = "security/comment_save", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> saveTripInfo(@RequestBody CommentModel commentModel) {
		try {
			commentModelService.saveComment(commentModel);
		} catch (Throwable e) {
			throw new RestException(e);
		}
		return Ajax.emptyResponse();
	}

}