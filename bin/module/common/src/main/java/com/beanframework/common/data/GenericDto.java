package com.beanframework.common.data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class GenericDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7199498187148793394L;
	public static final String UUID = "uuid";
	public static final String ID = "id";
	public static final String CREATED_DATE = "createdDate";
	public static final String CREATED_BY = "createdBy";
	public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
	public static final String LAST_MODIFIED_BY = "lastModifiedBy";

	private UUID uuid;
	private String id;
	private Date createdDate;
	private AuditorDto createdBy;
	private Date lastModifiedDate;
	private AuditorDto lastModifiedBy;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public AuditorDto getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(AuditorDto createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public AuditorDto getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(AuditorDto lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Override
	public String toString() {
		return "ID: " + id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericDto other = (GenericDto) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
