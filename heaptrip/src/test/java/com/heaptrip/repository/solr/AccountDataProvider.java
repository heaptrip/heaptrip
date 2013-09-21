package com.heaptrip.repository.solr;

import org.testng.annotations.DataProvider;

import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.repository.solr.entity.SolrAccount;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;
import com.heaptrip.domain.service.criteria.CheckModeEnum;
import com.heaptrip.domain.service.criteria.IDListCriteria;

public class AccountDataProvider {

	static final String ACCOUNT_ID = SolrAccountRepositoryTest.class.getName();

	@DataProvider(name = "solrAccount")
	public static Object[][] getSolrAccount() {
		SolrAccount account = new SolrAccount();
		account.setId(ACCOUNT_ID);
		account.setClazz(AccountEnum.USER.getClazz());
		account.setName("Иванов Петр Сергеевич");
		account.setCategories(new String[] { "1.1", "1.2", "1.3" });
		account.setRegions(new String[] { "2.1", "2.2", "2.3" });
		account.setFriends(new String[] { "3.1", "3.2", "3.3" });
		account.setPublishers(new String[] { "4.1", "4.2", "4.3" });
		account.setOwners(new String[] { "5.1", "5.2", "5.3" });
		account.setStaff(new String[] { "6.1", "6.2", "6.3" });
		account.setMembers(new String[] { "7.1", "7.2", "7.3" });
		return new Object[][] { new Object[] { account } };
	}

	@DataProvider(name = "accountSearchCriteria")
	public static Object[][] getAccountSearchCriteria() {
		AccountTextCriteria criteria = new AccountTextCriteria();
		criteria.setAccountType(AccountEnum.USER);
		criteria.setQuery("иванов");
		criteria.setCategories(new IDListCriteria(CheckModeEnum.IN, new String[] { "1.1", "1.3" }));
		criteria.setRegions(new IDListCriteria(CheckModeEnum.IN, new String[] { "2.2" }));
		criteria.setFriends(new IDListCriteria(CheckModeEnum.NOT_IN, new String[] { "3.4", "3.5" }));
		criteria.setPublishers(new IDListCriteria(CheckModeEnum.NOT_IN, new String[] { "4.4", "4.5" }));
		criteria.setOwners(new IDListCriteria(CheckModeEnum.IN, new String[] { "5.1", "5.2", "5.3" }));
		criteria.setStaff(new IDListCriteria(CheckModeEnum.IN, new String[] { "6.2" }));
		criteria.setMembers(new IDListCriteria(CheckModeEnum.NOT_IN, new String[] { "7.5" }));
		criteria.setSkip(0L);
		criteria.setLimit(1L);
		return new Object[][] { new Object[] { criteria } };
	}

}
