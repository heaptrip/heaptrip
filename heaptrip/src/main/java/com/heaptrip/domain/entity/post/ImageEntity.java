package com.heaptrip.domain.entity.post;


@Deprecated
public class ImageEntity extends BaseEntity {
	private String id;
	private String name;
	private Long size;

	@SuppressWarnings("unchecked")
	@Override
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public void setId(String id) {
		this.id = id;
	}

}
