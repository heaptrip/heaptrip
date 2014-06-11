package com.heaptrip.repository.mail;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.mail.Mail;
import com.heaptrip.domain.repository.mail.MailRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.util.collection.IteratorConverter;
import org.jongo.MongoCollection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailRepositoryImpl extends CrudRepositoryImpl<Mail> implements MailRepository {

    @Override
    protected Class<Mail> getCollectionClass() {
        return Mail.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.MAILS.getName();
    }

    @Override
    public List<Mail> findByNode(String node) {
        MongoCollection coll = getCollection();
        Iterable<Mail> iter = coll.find("{node: #}", node).sort("{created: -1}").as(getCollectionClass());
        return IteratorConverter.copyIterator(iter.iterator());
    }

}
