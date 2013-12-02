package com.heaptrip.repository.account;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.account.user.UserRegistration;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.Profile;
import com.heaptrip.domain.entity.account.Setting;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.mongodb.WriteResult;

@Repository
public class AccountRepositoryImpl extends CrudRepositoryImpl<Account> implements AccountRepository {

	protected static final Logger logger = LoggerFactory.getLogger(AccountRepositoryImpl.class);


    @Override
    public Account findAccountById(String userId) {
        MongoCollection coll = getCollection();
        String query = "{_id: #}";
        return coll.findOne(query, userId).as(Account.class);
    }

	@Override
	public void changeStatus(String accountId, AccountStatusEnum accountStatus) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$set: {'status': #}}";
		WriteResult wr = coll.update(query, accountId).with(updateQuery, accountStatus);
		logger.debug("WriteResult for update account: {}", wr);		
	}

	@Override
	public void changeEmail(String accountId, String newEmail) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$set: {'email': #}}";
		WriteResult wr = coll.update(query, accountId).with(updateQuery, newEmail);
		logger.debug("WriteResult for update account: {}", wr);
	}

	@Override
	public void saveSetting(String accountId, Setting setting) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$set: {'setting': #}}";
		WriteResult wr = coll.update(query, accountId).with(updateQuery, setting);
		logger.debug("WriteResult for update account: {}", wr);		
	}

	@Override
	public void saveProfile(String accountId, Profile profile) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$set: {'profile': #}}";
		WriteResult wr = coll.update(query, accountId).with(updateQuery, profile);
		logger.debug("WriteResult for update account: {}", wr);		
	}

	@Override
	public Account findByEmail(String email) {
		MongoCollection coll = getCollection();
		String query = "{email: #}";
		return coll.findOne(query, email).as(Account.class);
	}

	@Override
	protected String getCollectionName() {
		return Account.COLLECTION_NAME;
	}

	@Override
	protected Class<Account> getCollectionClass() {
		return Account.class;
	}

	@Override
	public AccountRating getRating(String accountId) {
		MongoCollection coll = getCollection();
		Account account = coll.findOne("{_id: #}", accountId).projection("{_class: 1, rating: 1}")
				.as(getCollectionClass());
		return (account == null) ? null : account.getRating();
	}

	@Override
	public void updateRating(String accountId, double ratingValue) {
		MongoCollection coll = getCollection();
		coll.update("{_id: #}", accountId).with("{$set: {rating.value: #}, $inc: {'rating.count': 1}}", ratingValue);
	}
}
