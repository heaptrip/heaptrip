package com.heaptrip.domain.service.adm;

import com.heaptrip.domain.entity.journal.JournalRecord;

/**
 * 
 * Service to work with system journal
 * 
 */
public interface JournalService {

	/**
	 * Add record to journal
	 * 
	 * @param record
	 * @return journal record
	 */
	public JournalRecord addRecord(JournalRecord record);

}
