package com.heaptrip.web.controller.profile;

import com.heaptrip.domain.service.account.relation.RelationService;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class ActionAccountController extends ExceptionHandlerControler {

    @Autowired
    @Qualifier("requestScopeService")
    private RequestScopeService scopeService;

    @Autowired
    RelationService relationService;

    @RequestMapping(value = "security/refusal_of_community", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> refusalOfCommunity(@RequestBody String guid) {
        try {
            relationService.deleteOwner(scopeService.getCurrentUser().getId(), guid);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "security/resign_from_community", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> resignFromCommunity(@RequestBody String guid) {
        try {
            relationService.deleteEmployee(scopeService.getCurrentUser().getId(), guid);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "security/out_of_community", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> outOfCommunity(@RequestBody String guid) {
        try {
            relationService.deleteMember(scopeService.getCurrentUser().getId(), guid);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "security/unsubscribe_from_community", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> unsubscribeFromCommunity(@RequestBody String guid) {
        try {
            relationService.deletePublisher(scopeService.getCurrentUser().getId(), guid);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "security/request_community_owner", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> sendOwnerRequest(@RequestBody String guid) {
        try {
            relationService.sendOwnerRequest(scopeService.getCurrentUser().getId(), guid);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "security/request_community_employee", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> sendEmployeeRequest(@RequestBody String guid) {
        try {
            relationService.sendEmployeeRequest(scopeService.getCurrentUser().getId(), guid);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "security/request_community_member", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> sendMemberRequest(@RequestBody String guid) {
        try {
            relationService.sendMemberRequest(scopeService.getCurrentUser().getId(), guid);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "security/add_publisher", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> addPublisher(@RequestBody String guid) {
        try {
            relationService.addPublisher(scopeService.getCurrentUser().getId(), guid);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }
}
