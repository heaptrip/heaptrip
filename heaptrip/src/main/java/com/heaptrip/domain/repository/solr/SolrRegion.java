package com.heaptrip.domain.repository.solr;

import java.util.Locale;

import com.heaptrip.domain.entity.LangEnum;
import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.Region;
import com.heaptrip.domain.entity.RegionEnum;
import com.heaptrip.util.LanguageUtils;

public class SolrRegion {

	private String id;
	private String parent;
	private String type;
	private String nameEn;
	private String nameRu;
	private String pathEn;
	private String pathRu;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameRu() {
		return nameRu;
	}

	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}

	public String getPathEn() {
		return pathEn;
	}

	public void setPathEn(String pathEn) {
		this.pathEn = pathEn;
	}

	public String getPathRu() {
		return pathRu;
	}

	public void setPathRu(String pathRu) {
		this.pathRu = pathRu;
	}

	public String getName(Locale locale) {
		String lang = LanguageUtils.getLanguageByLocale(locale);
		if (lang.equals(LangEnum.en.toString())) {
			return nameEn;
		} else {
			return nameRu;
		}
	}

	public String getPath(Locale locale) {
		String lang = LanguageUtils.getLanguageByLocale(locale);
		if (lang.equals(LangEnum.en.toString())) {
			return pathEn;
		} else {
			return pathRu;
		}
	}

	public Region toRegion(Locale locale) {
		Region result = new Region();
		result.setId(id);
		MultiLangText name = new MultiLangText();
		name.setValue(getName(locale), locale);
		result.setName(name);
		MultiLangText path = new MultiLangText();
		path.setValue(getPath(locale), locale);
		result.setPath(path);
		result.setParent(parent);
		if (type != null) {
			result.setType(RegionEnum.valueOf(type));
		}
		return result;
	}
}
