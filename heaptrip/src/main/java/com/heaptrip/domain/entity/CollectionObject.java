package com.heaptrip.domain.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jongo.marshall.jackson.oid.Id;

public abstract class CollectionObject {

	@Id
	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		CollectionObject bo = (CollectionObject) obj;
		if (getId() == null || bo.getId() == null) {
			return false;
		}
		return new EqualsBuilder().append(getId(), bo.getId()).isEquals();
	}

	@Override
	public int hashCode() {
		if (getId() == null) {
			return super.hashCode();
		}
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[id=" + getId() + "]";
	}
}
