package com.heaptrip.service.account.community;

import com.heaptrip.domain.entity.mail.MessageEnum;
import com.heaptrip.domain.entity.mail.MessageTemplate;
import com.heaptrip.domain.entity.mail.MessageTemplateStorage;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.system.MailService;
import com.heaptrip.domain.service.system.RequestScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.entity.account.community.CommunityProfile;
import com.heaptrip.domain.entity.account.community.CommunitySetting;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.community.CommunityRepository;
import com.heaptrip.domain.service.account.community.CommunityService;
import com.heaptrip.domain.service.system.ErrorService;
import com.heaptrip.service.account.AccountServiceImpl;

import java.util.Locale;

@Service
public class CommunityServiceImpl extends AccountServiceImpl implements CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private ErrorService errorService;

    @Autowired
    private MessageTemplateStorage messageTemplateStorage;

    @Autowired
    @Qualifier("requestScopeService")
    private RequestScopeService requestScopeService;

    @Autowired
    private MailService mailService;

    @Autowired
    private AccountStoreService accountStoreService;

    @Override
    public void delete(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");
        // TODO dikma после реализации сообществ, добавить проверку возможности удаления

        Account account = accountRepository.findOne(accountId);

        if (account == null) {
            String msg = String.format("account not find by id: %s", accountId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_FOUND);
        } else if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("account status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_ACTIVE);
        } else {
            accountRepository.changeStatus(accountId, AccountStatusEnum.DELETED);
            accountStoreService.remove(accountId);
        }
    }

    @Override
    public Community registration(Community community, Locale locale) {
        Assert.notNull(community, "account must not be null");
        Assert.notNull(community.getEmail(), "email must not be null");
        Assert.notNull(community.getTypeAccount(), "type account must not be null");
        Assert.isTrue(!community.getTypeAccount().equals(AccountEnum.USER), "account must not be type account is user");
        Assert.isTrue(community.getEmail().matches(EMAIL_REGEX), "email is not correct");

        // устанавливаем создателя сообщества
        if (requestScopeService.getCurrentUser() != null) {
            community.setOwnerAccountId(requestScopeService.getCurrentUser().getId());
        }

        community.setRating(AccountRating.getDefaultValue());
        Community com = communityRepository.save(community);

        MessageTemplate mt = messageTemplateStorage.getMessageTemplate(MessageEnum.CONFIRM_REGISTRATION);

        StringBuilder str = new StringBuilder();
        str.append(requestScopeService.getCurrentContextPath());
        str.append("/mail/registration/confirm?");
        str.append("uid=").append(com.getId());
        str.append("&value=").append(com.getId().hashCode());

        String msg = String.format(mt.getText(locale), str.toString());
        mailService.sendNoreplyMessage(com.getEmail(), mt.getSubject(locale), msg);

        return com;
    }

}
