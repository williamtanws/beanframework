package com.beanframework.common.data;

import java.util.HashSet;
import java.util.Set;

public class HistoryDataResponse {

	Object entity;
	String revisionId;
	String revisionDate;
	String revisionType;
	Set<String> propertiesChanged = new HashSet<String>();

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public String getRevisionId() {
		return revisionId;
	}

	public void setRevisionId(String revisionId) {
		this.revisionId = revisionId;
	}

	public String getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getRevisionType() {
		return revisionType;
	}

	public void setRevisionType(String revisionType) {
		this.revisionType = revisionType;
	}

	public Set<String> getPropertiesChanged() {
		return propertiesChanged;
	}

	public void setPropertiesChanged(Set<String> propertiesChanged) {
		this.propertiesChanged = propertiesChanged;
	}

}
