package com.heaptrip.domain.repository.solr;

import com.heaptrip.domain.repository.solr.entity.SolrAccount;
import com.heaptrip.domain.repository.solr.entity.SolrAccountSearchReponse;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

public interface SolrAccountRepository {

    public void save(SolrAccount account) throws SolrServerException, IOException;

    public void remove(String accountId) throws SolrServerException, IOException;

    public boolean exists(String accountId) throws SolrServerException;

    /**
     * Commit changed to Apache Solr.
     * Used only in tests.
     *
     * @throws IOException
     * @throws SolrServerException
     */
    public void commit() throws IOException, SolrServerException;

    public SolrAccountSearchReponse findByAccountSearchCriteria(AccountTextCriteria criteria)
            throws SolrServerException;

}
