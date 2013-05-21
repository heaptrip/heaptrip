package com.heaptrip.web.model.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryModel {

	private String id;
	private String data;
	private boolean checked;
	private Map<String, String> attr;
	private List<CategoryModel> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		buildAttr();
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
		buildAttr();
	}

	public Map<String, String> getAttr() {
		return attr;
	}

	private void buildAttr() {
		if (attr == null)
			attr = new HashMap<String, String>();
		if (id != null)
			attr.put("id", id);
		if (checked)
			attr.put("class", "jstree-checked");
	}

	public List<CategoryModel> getChildren() {
		return children;
	}

	public void setChildren(List<CategoryModel> children) {
		this.children = children;
	}

	public void addChildren(CategoryModel categoryModel) {
		if (children == null)
			children = new ArrayList<CategoryModel>();
		children.add(categoryModel);
	}

}
