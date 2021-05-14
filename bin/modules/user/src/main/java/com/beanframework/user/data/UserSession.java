package com.beanframework.user.data;

import java.util.List;
import org.springframework.security.core.session.SessionInformation;
import com.beanframework.user.domain.User;

public class UserSession {

  private User user;
  private List<SessionInformation> sessionInformations;

  public UserSession(User user, List<SessionInformation> sessionInformations) {
    super();
    this.user = user;
    this.sessionInformations = sessionInformations;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<SessionInformation> getSessionInformations() {
    return sessionInformations;
  }

  public void setSessionInformations(List<SessionInformation> sessionInformations) {
    this.sessionInformations = sessionInformations;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((user.getId() == null) ? 0 : user.getId().hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UserSession other = (UserSession) obj;
    if (user.getId() == null) {
      if (other.user.getId() != null)
        return false;
    } else if (!user.getId().equals(other.user.getId()))
      return false;
    return true;
  }
}
