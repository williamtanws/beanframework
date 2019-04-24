package com.beanframework.comment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.comment.CommentConstants;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.user.domain.User;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = CommentConstants.Table.COMMENT)
public class Comment extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6793982140563472301L;
	public static final String HTML = "html";
	public static final String VISIBLED = "visibled";
	public static final String REPLIEDTO = "repliedTo";
	public static final String USER = "user";

	@Audited(withModifiedFlag = true)
	@Lob
	@Column(length = 100000)
	private String html;

	@Audited(withModifiedFlag = true)
	private Boolean visibled;

	@Audited(withModifiedFlag = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_uuid")
	private User user;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Boolean getVisibled() {
		return visibled;
	}

	public void setVisibled(Boolean visibled) {
		this.visibled = visibled;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
