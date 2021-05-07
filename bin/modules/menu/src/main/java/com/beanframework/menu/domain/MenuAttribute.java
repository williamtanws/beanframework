package com.beanframework.menu.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.menu.MenuConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = MenuConstants.Table.MENU_ATTRIBUTE)
public class MenuAttribute extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7666190244677961254L;
	public static final String MENU = "menu";
	public static final String DYNAMIC_FIELD_SLOT = "dynamicFieldSlot";
	
	@JsonIgnore
	@Audited(withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_uuid")
	private Menu menu;

	@Audited(withModifiedFlag = true)
	@Column(name = "dynamicfieldslot_uuid", columnDefinition = "BINARY(16)")
	private UUID dynamicFieldSlot;

	@Audited(withModifiedFlag = true)
	private String value;

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public UUID getDynamicFieldSlot() {
		return dynamicFieldSlot;
	}

	public void setDynamicFieldSlot(UUID dynamicFieldSlot) {
		this.dynamicFieldSlot = dynamicFieldSlot;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
