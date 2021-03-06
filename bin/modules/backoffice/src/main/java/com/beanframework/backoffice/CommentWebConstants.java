package com.beanframework.backoffice;

public interface CommentWebConstants {

  public interface Path {

    public interface Api {
      public static final String COMMENT = "${path.api.comment}";
      public static final String COMMENT_HISTORY = "${path.api.comment.history}";
      public static final String COMMENT_CHECKID = "${path.api.comment.checkid}";
    }

    public static final String COMMENT = "${path.comment}";
    public static final String COMMENT_FORM = "${path.comment.form}";
  }

  public interface View {
    public static final String COMMENT = "${view.comment}";
    public static final String COMMENT_FORM = "${view.comment.form}";
  }

  public interface ModelAttribute {
    public static final String COMMENT_DTO = "commentDto";
  }

  public interface CommentPreAuthorizeEnum {
    public static final String AUTHORITY_READ = "comment_read";
    public static final String AUTHORITY_CREATE = "comment_create";
    public static final String AUTHORITY_UPDATE = "comment_update";
    public static final String AUTHORITY_DELETE = "comment_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
