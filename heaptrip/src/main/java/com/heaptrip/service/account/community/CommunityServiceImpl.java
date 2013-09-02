package com.heaptrip.service.account.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.entity.account.community.CommunityProfile;
import com.heaptrip.domain.entity.account.community.CommunitySetting;
import com.heaptrip.domain.repository.account.community.CommunityRepository;
import com.heaptrip.domain.service.account.community.CommunityService;
import com.heaptrip.service.account.AccountServiceImpl;

@Service
public class CommunityServiceImpl extends AccountServiceImpl implements CommunityService {

	@Autowired
	private CommunityRepository communityRepository;
	
	@Override
	public void delete(String accountId) {
		Assert.notNull(accountId, "accountId must not be null");
		// TODO dikma после реализации сообществ, добавить проверку возможности удаления 
		
		Account account = accountRepository.findOne(accountId);
		
		if (account == null) {
			String msg = String.format("account not find by id: %s", accountId);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("account status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			accountRepository.changeStatus(accountId, AccountStatusEnum.DELETED);
		}
	}

	@Override
	public Community registration(Community community) {
		Assert.notNull(community, "account must not be null");
		Assert.notNull(community.getEmail(), "email must not be null");
		Assert.notNull(community.getTypeAccount(), "type account must not be null");
		Assert.isTrue(!community.getTypeAccount().equals(AccountEnum.USER), "account must not be type account is user");
		Assert.isTrue(community.getEmail().matches(EMAIL_REGEX), "email is not correct");
		
		community.setProfile(new CommunityProfile());
		community.setSetting(new CommunitySetting());
		community.setStatus(AccountStatusEnum.NOTCONFIRMED);
		
		return communityRepository.save(community);
	}

}
