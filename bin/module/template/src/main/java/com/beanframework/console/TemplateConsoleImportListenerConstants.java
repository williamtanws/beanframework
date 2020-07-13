package com.beanframework.console;

import com.beanframework.configuration.domain.Configuration;
import com.beanframework.core.csv.TemplateCsv;
import com.beanframework.template.domain.Template;

public interface TemplateConsoleImportListenerConstants {

	public interface TemplateImport {
		public static final String TYPE = Configuration.class.getSimpleName();
		public static final String NAME = "Configuration";
		public static final int SORT = 10;
		public static final String DESCRIPTION = "Update/Remove Configuration Data";
		public static final Class<TemplateCsv> CLASS_CSV = TemplateCsv.class;
		public static final Class<Template> CLASS_ENTITY = Template.class;
	}
}
