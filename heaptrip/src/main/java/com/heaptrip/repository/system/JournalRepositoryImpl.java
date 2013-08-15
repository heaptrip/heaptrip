package com.heaptrip.repository.system;

import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.journal.JournalRecord;
import com.heaptrip.domain.repository.system.JournalRepository;
import com.heaptrip.repository.CrudRepositoryImpl;

@Service
public class JournalRepositoryImpl extends CrudRepositoryImpl<JournalRecord> implements JournalRepository {

	@Override
	protected String getCollectionName() {
		return JournalRecord.COLLECTION_NAME;
	}

	@Override
	protected Class<JournalRecord> getCollectionClass() {
		return JournalRecord.class;
	}

}
