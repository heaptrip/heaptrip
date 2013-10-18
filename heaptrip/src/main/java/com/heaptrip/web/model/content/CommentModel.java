package com.heaptrip.web.model.content;

import java.util.ArrayList;
import java.util.List;

import com.heaptrip.web.model.profile.AccountModel;

public class CommentModel {

	private String id;
	private String target;
	
	// TRIP OR POST OR ETC. 
	private String targetClass;
	private String parent;
	private AccountModel author;
	private DateModel created;
	private String text;
	
	private List<CommentModel> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public AccountModel getAuthor() {
		return author;
	}

	public void setAuthor(AccountModel author) {
		this.author = author;
	}

	public DateModel getCreated() {
		return created;
	}

	public void setCreated(DateModel created) {
		this.created = created;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<CommentModel> getChildren() {
		return children;
	}

	public void setChildren(List<CommentModel> children) {
		this.children = children;
	}

	public void addChildren(CommentModel commentModel) {
		if (children == null)
			children = new ArrayList<CommentModel>();
		children.add(commentModel);
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

}
