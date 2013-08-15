package com.heaptrip.domain.entity.journal;

import java.util.Date;

import com.heaptrip.domain.entity.BaseObject;

/**
 * 
 * Journal to record errors
 * 
 */
public class JournalRecord extends BaseObject {

	public static final String COLLECTION_NAME = "journal";

	// module
	private ModuleEnum module;

	// record level
	private RecordLevelEnum level;

	// message text
	private String message;

	// create date
	private Date created;

	// stack trace
	private String[] trace;

	public ModuleEnum getModule() {
		return module;
	}

	public void setModule(ModuleEnum module) {
		this.module = module;
	}

	public RecordLevelEnum getLevel() {
		return level;
	}

	public void setLevel(RecordLevelEnum level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String[] getTrace() {
		return trace;
	}

	public void setTrace(String[] trace) {
		this.trace = trace;
	}

}
