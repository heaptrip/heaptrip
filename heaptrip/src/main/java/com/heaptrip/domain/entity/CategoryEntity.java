package com.heaptrip.domain.entity;

@Deprecated
public class CategoryEntity extends BaseEntity {
	public static final String COLLECTION_NAME = "categories";

	private String id;
	private String parentId;
	private String nameRu;
	private String nameEn;

	public CategoryEntity() {
		super();
	}

	public CategoryEntity(String id, String parentId, String nameRu, String nameEn) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.nameRu = nameRu;
		this.nameEn = nameEn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getId() {
		return id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getNameRu() {
		return nameRu;
	}

	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public void setId(String id) {
		this.id = id;
	}

}
