package com.beanframework.documentation;

public class DocumentationConstants {

  public interface Path {
    public static final String DOCUMENTATION = "${path.documentation}";
  }

  public interface View {
    public static final String DOCUMENTATION = "${view.documentation}";
  }

  public static interface Access {
    public static final String DOCUMENTATION = "${module.documentation.access}";
  }

  public interface DocumentationPreAuthorizeEnum {
    public static final String DOCUMENTATION_READ = "documentation_read";

    public static final String HAS_READ = "hasAuthority('" + DOCUMENTATION_READ + "')";
  }
}
