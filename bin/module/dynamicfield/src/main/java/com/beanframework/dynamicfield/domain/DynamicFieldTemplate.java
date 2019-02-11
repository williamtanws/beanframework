package com.beanframework.dynamicfield.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.dynamicfield.DynamicFieldTemplateConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = DynamicFieldTemplateConstants.Table.DYNAMIC_FIELD_TEMPLATE)
public class DynamicFieldTemplate extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 250602072404640389L;

	public static final String NAME = "name";

	@Audited(withModifiedFlag = true)
	private String name;

	@AuditMappedBy(mappedBy = DynamicFieldSlot.DYNAMIC_FIELD_TEMPLATE)
	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = DynamicFieldSlot.DYNAMIC_FIELD_TEMPLATE, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<DynamicFieldSlot> dynamicFieldSlots = new ArrayList<DynamicFieldSlot>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DynamicFieldSlot> getDynamicFieldSlots() {
		return dynamicFieldSlots;
	}

	public void setDynamicFieldSlots(List<DynamicFieldSlot> dynamicFieldSlots) {
		this.dynamicFieldSlots = dynamicFieldSlots;
	}

}
