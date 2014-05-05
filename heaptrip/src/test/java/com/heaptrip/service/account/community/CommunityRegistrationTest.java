package com.heaptrip.service.account.community;

import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.RelationTypeEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.repository.solr.SolrAccountRepository;
import com.heaptrip.domain.repository.solr.entity.SolrAccountSearchReponse;
import com.heaptrip.domain.service.account.community.CommunityService;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;
import com.heaptrip.domain.service.account.criteria.relation.AccountRelationCriteria;
import com.heaptrip.domain.service.account.criteria.relation.RelationCriteria;
import com.heaptrip.domain.service.account.criteria.relation.UserRelationCriteria;
import com.heaptrip.domain.service.criteria.CheckModeEnum;
import com.heaptrip.domain.service.criteria.IDListCriteria;
import com.heaptrip.security.Authenticate;
import com.heaptrip.security.AuthenticationListener;
import com.heaptrip.service.account.user.UserDataProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
@Listeners(AuthenticationListener.class)
public class CommunityRegistrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private SolrAccountRepository solrAccountRepository;

    private Community notConfirmedCommunity;

    @Test(enabled = true, priority = 1)
    @Authenticate(userid = "email", username = "Email User", roles = "ROLE_USER")
    public void registrationСlub() throws ExecutionException, InterruptedException, IOException, SolrServerException {
        Locale locale = new Locale("ru");

        Community community = communityService.registration(CommunityDataProvider.getClub(), locale);

        Future<Void> future = communityService.confirmRegistration(community.getId(), community.getSendValue());
        future.get();

        solrAccountRepository.commit();

        String[] typeRelations = new String[1];
        typeRelations[0] = RelationTypeEnum.OWNER.toString();
        long count = relationRepository.countByRelationCriteria(new RelationCriteria(CommunityDataProvider.COMMUNITY_ID, UserDataProvider.EMAIL_USER_ID, typeRelations));

        Assert.assertTrue(count == 1);

        AccountTextCriteria criteria = new AccountTextCriteria();
        criteria.setQuery(CommunityDataProvider.COMMUNITY_NAME);
        criteria.setOwners(new IDListCriteria(CheckModeEnum.IN, new String[]{UserDataProvider.EMAIL_USER_ID}));
        criteria.setSkip(0L);
        criteria.setLimit(1L);

        SolrAccountSearchReponse response = solrAccountRepository.findByAccountSearchCriteria(criteria);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.getNumFound() > 0);
        Assert.assertNotNull(response.getAccountIds());
        Assert.assertTrue(response.getAccountIds().length > 0);

        for (String accountId : response.getAccountIds()) {
            Assert.assertTrue(StringUtils.isNotBlank(accountId));
        }
    }

    @Test(enabled = true, priority = 2)
    public void registrationNotConfirmedClub() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        notConfirmedCommunity = communityService.registration(CommunityDataProvider.getNotConfirmedClub(), locale);
    }

    @Test(enabled = true, priority = 5, expectedExceptions = AccountException.class)
    public void confirmWrongCommunityId() {
        communityService.confirmRegistration(CommunityDataProvider.FAKE_COMMUNITY_ID, notConfirmedCommunity.getSendValue());
    }

    @Test(enabled = true, priority = 6, expectedExceptions = AccountException.class)
    public void confirmWrongSendValue() {
        communityService.confirmRegistration(notConfirmedCommunity.getId(), notConfirmedCommunity.getId());
    }
}
