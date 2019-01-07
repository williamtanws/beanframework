package com.beanframework.menu.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericDomain;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.menu.MenuConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = MenuConstants.Table.MENU_FIELD)
public class MenuField extends GenericDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7666190244677961254L;
	public static final String MENU = "menu";
	public static final String USER_RIGHT = "menu";
	public static final String DYNAMIC_FIELD = "dynamicField";

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_uuid")
	private Menu menu;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dynamicfield_uuid")
	@OrderBy(DynamicField.SORT+" ASC")
	private DynamicField dynamicField;

	@Audited(withModifiedFlag = true)
	private String value;

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public DynamicField getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(DynamicField dynamicField) {
		this.dynamicField = dynamicField;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
