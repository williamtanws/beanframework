package com.beanframework.menu.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.AbstractDomain;
import com.beanframework.language.domain.Language;
import com.beanframework.menu.MenuConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = MenuConstants.Table.MENU_LANG)
public class MenuLang extends AbstractDomain {

	private static final long serialVersionUID = 3253296818921675586L;
	public static final String MODEL = "MenuLang";
	public static final String NAME = "name";
	public static final String LANGUAGE = "language";
	public static final String MENU = "menu";

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "language_uuid")
	private Language language;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_uuid")
	private Menu menu;

	private String name;

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
