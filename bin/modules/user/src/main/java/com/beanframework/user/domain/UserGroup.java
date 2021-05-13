package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.user.UserGroupConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserGroupConstants.Table.USER_GROUP)
public class UserGroup extends GenericEntity {

  /**
   * 
   */
  private static final long serialVersionUID = -2986635395842071353L;
  public static final String NAME = "name";
  public static final String USERS = "users";
  public static final String USER_GROUPS = "userGroups";
  public static final String USER_AUTHORITIES = "userAuthorities";
  public static final String ATTRIBUTES = "fields";

  public UserGroup() {
    super();
  }

  public UserGroup(UUID uuid, String id, String name) {
    super();
    setUuid(uuid);
    setId(id);
    setName(name);
  }

  @Audited(withModifiedFlag = true)
  private String name;

  @Audited(withModifiedFlag = true)
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = UserGroupConstants.Table.USER_GROUP_USER_GROUP_REL,
      joinColumns = @JoinColumn(name = "usergroup_uuid"))
  @Column(name = "usergrouprel_uuid", columnDefinition = "BINARY(16)", nullable = false)
  private Set<UUID> userGroups = new HashSet<UUID>();

  @Audited(withModifiedFlag = true)
  @Cascade({CascadeType.ALL})
  @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
  private List<UserAuthority> userAuthorities = new ArrayList<UserAuthority>();

  @Audited(withModifiedFlag = true)
  @Cascade({CascadeType.ALL})
  @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
  @Fetch(value = FetchMode.SUBSELECT)
  @OrderBy(UserGroupAttribute.DYNAMIC_FIELD_SLOT)
  private List<UserGroupAttribute> attributes = new ArrayList<UserGroupAttribute>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<UUID> getUserGroups() {
    return userGroups;
  }

  public void setUserGroups(Set<UUID> userGroups) {
    this.userGroups = userGroups;
  }

  public List<UserAuthority> getUserAuthorities() {
    return userAuthorities;
  }

  public void setUserAuthorities(List<UserAuthority> userAuthorities) {
    this.userAuthorities = userAuthorities;
  }

  public List<UserGroupAttribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<UserGroupAttribute> attributes) {
    this.attributes = attributes;
  }
}
