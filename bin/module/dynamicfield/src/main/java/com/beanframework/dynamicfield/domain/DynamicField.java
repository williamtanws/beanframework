package com.beanframework.dynamicfield.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.dynamicfield.DynamicFieldConstants;
import com.beanframework.dynamicfield.DynamicFieldType;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.language.domain.Language;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = DynamicFieldConstants.Table.DYNAMIC_FIELD)
public class DynamicField extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4733112810509713059L;
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String SORT = "sort";
	public static final String REQUIRED = "required";
	public static final String RULE = "rule";
	public static final String LABEL = "label";
	public static final String GRID = "grid";
	public static final String LANGUAGE = "language";
	public static final String ENUMERATIONS = "enumerations";

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(withModifiedFlag = true)
	@NotNull
	@Enumerated(EnumType.STRING)
	private DynamicFieldType type;

	@Audited(withModifiedFlag = true)
	private Boolean required;

	@Audited(withModifiedFlag = true)
	private String rule;

	@Audited(withModifiedFlag = true)
	private String label;

	@Audited(withModifiedFlag = true)
	private String grid;

	@Audited(withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "language_uuid")
	private Language language;

	@AuditJoinTable(inverseJoinColumns = @JoinColumn(name = "enumeration_uuid"))
	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.REFRESH })
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = DynamicFieldConstants.Table.DYNAMIC_FIELD_ENUMERATION_REL, joinColumns = @JoinColumn(name = "dynamicfield_uuid", referencedColumnName = "uuid"), inverseJoinColumns = @JoinColumn(name = "enumeration_uuid", referencedColumnName = "uuid"))
	private List<Enumeration> enumerations = new ArrayList<Enumeration>();

	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.REFRESH })
	@OneToMany(fetch = FetchType.LAZY)
	private List<DynamicFieldSlot> dynamicFieldSlots = new ArrayList<DynamicFieldSlot>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DynamicFieldType getType() {
		return type;
	}

	public void setType(DynamicFieldType type) {
		this.type = type;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public List<Enumeration> getEnumerations() {
		return enumerations;
	}

	public void setEnumerations(List<Enumeration> enumerations) {
		this.enumerations = enumerations;
	}

	public List<DynamicFieldSlot> getDynamicFieldSlots() {
		return dynamicFieldSlots;
	}

	public void setDynamicFieldSlots(List<DynamicFieldSlot> dynamicFieldSlots) {
		this.dynamicFieldSlots = dynamicFieldSlots;
	}

}
