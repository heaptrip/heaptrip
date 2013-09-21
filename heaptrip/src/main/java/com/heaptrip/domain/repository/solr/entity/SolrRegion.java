package com.heaptrip.domain.repository.solr.entity;

import java.util.Locale;

import com.heaptrip.domain.entity.LangEnum;
import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.entity.region.RegionEnum;
import com.heaptrip.util.language.LanguageUtils;

public class SolrRegion extends SolrDocument {

	private String parent;
	private String type;
	private String nameEn;
	private String nameRu;
	private String pathEn;
	private String pathRu;

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
		if (lang.equals(LangEnum.EN.getValue())) {
			return nameEn;
		} else {
			return nameRu;
		}
	}

	public String getPath(Locale locale) {
		String lang = LanguageUtils.getLanguageByLocale(locale);
		if (lang.equals(LangEnum.EN.getValue())) {
			return pathEn;
		} else {
			return pathRu;
		}
	}

	public Region toRegion(Locale locale) {
		Region result = new Region();
		// set id
		result.setId(id);
		// set name
		MultiLangText name = new MultiLangText();
		name.setValue(getName(locale), locale);
		result.setName(name);
		// set path
		MultiLangText path = new MultiLangText();
		path.setValue(getPath(locale), locale);
		result.setPath(path);
		// set parent
		result.setParent(parent);
		// set type
		if (type != null) {
			result.setType(RegionEnum.valueOf(type));
		}
		return result;
	}
}
