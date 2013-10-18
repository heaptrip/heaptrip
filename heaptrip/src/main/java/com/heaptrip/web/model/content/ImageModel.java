package com.heaptrip.web.model.content;

public class ImageModel {

	private String id;
	// image name
	private String name;
	// image text (description)
	private String text;

	public ImageModel() {

	}

	public ImageModel(String id) {
		setId(id);
	}

	public ImageModel(String id, String name) {
		setId(id);
		setName(name);
	}

	public ImageModel(String id, String name, String text) {
		setId(id);
		setName(name);
		setText(text);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
