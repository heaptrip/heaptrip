package com.heaptrip.service.adm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.journal.JournalRecord;
import com.heaptrip.domain.repository.system.JournalRepository;
import com.heaptrip.domain.service.adm.JournalService;

@Service
public class JournalServiceImpl implements JournalService {

	@Autowired
	private JournalRepository journalRepository;

	@Override
	public JournalRecord addRecord(JournalRecord record) {
		return journalRepository.save(record);
	}

}
