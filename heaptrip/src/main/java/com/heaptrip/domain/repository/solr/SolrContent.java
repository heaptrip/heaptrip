package com.heaptrip.domain.repository.solr;

public class SolrContent extends SolrDocument {

	private String clazz;
	private String nameEn;
	private String textEn;
	private String nameRu;
	private String textRu;

	public String getClazz() {
		return clazz;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getTextEn() {
		return textEn;
	}

	public void setTextEn(String textEn) {
		this.textEn = textEn;
	}

	public String getNameRu() {
		return nameRu;
	}

	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}

	public String getTextRu() {
		return textRu;
	}

	public void setTextRu(String textRu) {
		this.textRu = textRu;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[id=" + getId() + ", clazz=" + getClazz() + ", nameRu=" + getNameRu()
				+ ", nameEn=" + getNameEn() + ", textRu=" + getTextRu() + ", textEn=" + getTextEn() + "]";
	}

}
