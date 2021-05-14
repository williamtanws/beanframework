package com.beanframework.backoffice;

public interface EmployeeWebConstants {

  public interface Path {

    public interface Api {
      public static final String EMPLOYEE = "${path.api.employee}";
      public static final String EMPLOYEE_HISTORY = "${path.api.employee.history}";
      public static final String EMPLOYEE_CHECKID = "${path.api.employee.checkid}";
    }

    public static final String EMPLOYEE = "${path.employee}";
    public static final String EMPLOYEE_FORM = "${path.employee.form}";
  }

  public interface View {
    public static final String EMPLOYEE = "${view.employee}";
    public static final String EMPLOYEE_FORM = "${view.employee.form}";
  }

  public interface ModelAttribute {
    public static final String EMPLOYEE_DTO = "employeeDto";
  }

  public interface EmployeePreAuthorizeEnum {
    public static final String AUTHORITY_READ = "employee_read";
    public static final String AUTHORITY_CREATE = "employee_create";
    public static final String AUTHORITY_UPDATE = "employee_update";
    public static final String AUTHORITY_DELETE = "employee_delete";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
    public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
    public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
    public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
  }
}
