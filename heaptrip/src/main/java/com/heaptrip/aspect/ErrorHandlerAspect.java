package com.heaptrip.aspect;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.journal.JournalRecord;
import com.heaptrip.domain.entity.journal.ModuleEnum;
import com.heaptrip.domain.entity.journal.RecordLevelEnum;
import com.heaptrip.domain.exception.BaseException;
import com.heaptrip.domain.exception.Journalable;
import com.heaptrip.domain.service.adm.JournalService;
import com.heaptrip.domain.service.category.CategoryService;

@Aspect
@Service
public class ErrorHandlerAspect {

	private static final Logger logger = LoggerFactory.getLogger(ErrorHandlerAspect.class);

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private JournalService journalService;

	@Pointcut("execution(* com.heaptrip.service..*(..))")
	public void inServiceLayer() {
	}

	@AfterThrowing(pointcut = "inServiceLayer()", throwing = "e")
	public void doBeforeTask(JoinPoint joinPoint, Throwable e) {
		logger.error("An exception " + e + " has been thrown in " + joinPoint.getSignature().getName() + "()");
		if (e instanceof Journalable) {
			JournalRecord record = toJournalRecord(e);
			journalService.addRecord(record);
			logger.debug("Add exception to journal", e);
		}
	}

	private JournalRecord toJournalRecord(Throwable e) {
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
		return record;
	}
}