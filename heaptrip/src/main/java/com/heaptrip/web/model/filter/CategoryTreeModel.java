package com.heaptrip.web.model.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heaptrip.web.model.content.CategoryModel;

public class CategoryTreeModel extends CategoryModel {

	private boolean checked;
	private Map<String, String> attr;
	private List<CategoryTreeModel> children;

	@Override
	public void setId(String id) {
		this.id = id;
		buildAttr();
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

	public List<CategoryTreeModel> getChildren() {
		return children;
	}

	public void setChildren(List<CategoryTreeModel> children) {
		this.children = children;
	}

	public void addChildren(CategoryTreeModel categoryModel) {
		if (children == null)
			children = new ArrayList<CategoryTreeModel>();
		children.add(categoryModel);
	}

}
