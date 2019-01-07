package com.beanframework.common.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "auditor")
public class Auditor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8890339553608336532L;

	@Id
	@Column(columnDefinition = "BINARY(16)", unique = true, updatable = false)
	private UUID uuid;

	@NotBlank
	@Column(unique = true)
	private String id;

	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
