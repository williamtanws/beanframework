package com.beanframework.cms.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Lob;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.beanframework.cms.CommentConstants;
import com.beanframework.common.domain.GenericEntity;

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

  public Comment() {
    super();
  }

  public Comment(UUID uuid, UUID user, String html) {
    super();
    setUuid(uuid);
    this.html = html;
  }

  @Audited(withModifiedFlag = true)
  @Lob
  @Column(length = 100000)
  private String html;

  @Audited(withModifiedFlag = true)
  private Boolean visibled;

  @Audited(withModifiedFlag = true)
  @Column(name = "user_uuid", columnDefinition = "BINARY(16)")
  private UUID user;

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

  public UUID getUser() {
    return user;
  }

  public void setUser(UUID user) {
    this.user = user;
  }
}
