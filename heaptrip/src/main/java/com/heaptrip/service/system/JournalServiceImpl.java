package com.heaptrip.service.system;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.journal.JournalRecord;
import com.heaptrip.domain.entity.journal.ModuleEnum;
import com.heaptrip.domain.entity.journal.RecordLevelEnum;
import com.heaptrip.domain.exception.BaseException;
import com.heaptrip.domain.repository.system.JournalRepository;
import com.heaptrip.domain.service.system.JournalService;

@Service
public class JournalServiceImpl implements JournalService {

	@Autowired
	private JournalRepository journalRepository;

	@Override
	public JournalRecord addRecord(JournalRecord record) {
		return journalRepository.save(record);
	}

	@Override
	public JournalRecord addError(Throwable e) {
		JournalRecord record = new JournalRecord();
		record.setLevel(RecordLevelEnum.ERROR);
		record.setTrace(ExceptionUtils.getStackFrames(e));
		record.setMessage(e.getLocalizedMessage());
		record.setCreated(new Date());
		if (e instanceof BaseException) {
			record.setModule(((BaseException) e).getModule());
		} else {
			record.setModule(ModuleEnum.UNKNOWN);
		}
		return addRecord(record);
	}

}
