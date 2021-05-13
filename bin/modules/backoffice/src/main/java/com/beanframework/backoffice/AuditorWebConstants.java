package com.beanframework.backoffice;

public interface AuditorWebConstants {

  public interface Path {

    public static final String AUDITOR = "${path.auditor}";

    public interface Api {
      public static final String AUDITOR = "${path.api.auditor}";
      public static final String HISTORY = "${path.api.auditor.history}";
    }
  }

  public interface View {
    public static final String AUDITOR = "${view.auditor}";
  }

  public interface ModelAttribute {

    public static final String AUDITOR_DTO = "auditorDto";
  }
}
