package com.heaptrip.domain.service.system;

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

	/**
	 * Add exception to journal
	 * 
	 * @param e
	 *            exception
	 * @return journal record
	 */
	public JournalRecord addError(Throwable e);

}
