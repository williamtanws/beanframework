package com.beanframework.dynamicfield;

public class DynamicFieldTemplateConstants {

  public static interface Table {
    public static final String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE = "dynamicfieldtpl";
    public static final String DYNAMIC_FIELD_TEMPLATE_REL = "dynamicfieldtpl_rel";
    public static final String DYNAMIC_FIELD_TEMPLATE_FIELDSLOT_REL = "dynamicfieldtpl_slotrel";;
  }

  public static interface Locale {
    public static final String ID_REQUIRED = "module.dynamicfieldtemplate.id.required";
    public static final String ID_EXISTS = "module.dynamicfieldtemplate.id.exists";
    public static final String UUID_NOT_EXISTS = "module.dynamicfieldtemplate.uuid.notexists";;
  }
}
