package com.beanframework.dynamicfield.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.dynamicfield.DynamicFieldTemplateConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = DynamicFieldTemplateConstants.Table.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
public class DynamicFieldTemplate extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 250602072404640389L;

	public static final String NAME = "name";
	public static final String DYNAMIC_FIELD_SLOTS = "dynamicFieldSlots";

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@Cascade({ CascadeType.ALL })
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = DynamicFieldTemplateConstants.Table.DYNAMIC_FIELD_TEMPLATE_FIELDSLOT_REL, joinColumns = @JoinColumn(name = "template_uuid", referencedColumnName = "uuid"), inverseJoinColumns = @JoinColumn(name = "slot_uuid", referencedColumnName = "uuid"))
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
