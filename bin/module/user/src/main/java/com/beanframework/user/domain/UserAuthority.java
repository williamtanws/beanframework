package com.beanframework.user.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.user.UserConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserConstants.Table.USER_AUTHORITY)
public class UserAuthority extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5421555853972873801L;
	public static final String USER_GROUP = "userGroup";
	public static final String USER_PERMISSION = "userPermission";
	public static final String USER_PERMISSION_UUID = "userPermission.uuid";
	public static final String USER_RIGHT = "userRight";

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usergroup_uuid")
	private UserGroup userGroup;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@NotAudited
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userpermission_uuid")
	private UserPermission userPermission;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
	@NotAudited
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userright_uuid")
	private UserRight userRight;

	@Audited(withModifiedFlag = true)
	@NotNull
	private Boolean enabled;

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public UserPermission getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(UserPermission userPermission) {
		this.userPermission = userPermission;
	}

	public UserRight getUserRight() {
		return userRight;
	}

	public void setUserRight(UserRight userRight) {
		this.userRight = userRight;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}