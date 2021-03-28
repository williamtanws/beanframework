package com.beanframework.common.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class GenericBaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7891766433045085186L;
	@Id
	@GeneratedValue(generator = "inquisitive-uuid2")
	@GenericGenerator(name = "inquisitive-uuid2", strategy = "com.beanframework.common.domain.InquisitiveUUIDGenerator")
	@Column(columnDefinition = "BINARY(16)", unique = true, updatable = false)
	private UUID uuid;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
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
		GenericBaseEntity other = (GenericBaseEntity) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
