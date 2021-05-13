package com.beanframework.console;

public interface UpdateWebConstants {

  public interface Path {
    public static final String UPDATE = "${path.update}";

    public interface Api {

      public static final String UPDATE_TREE = "${path.api.update.tree}";
    }
  }

  public interface View {

    public static final String UPDATE = "${view.update}";
  }
}
