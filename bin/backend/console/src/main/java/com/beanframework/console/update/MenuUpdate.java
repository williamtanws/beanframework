package com.beanframework.console.update;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.Updater;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.MenuCsv;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldTypeEnum;
import com.beanframework.language.domain.Language;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuField;
import com.beanframework.menu.domain.MenuTargetTypeEnum;
import com.beanframework.user.domain.UserGroup;

public class MenuUpdate extends Updater {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.menu}")
	private String MENU_IMPORT_UPDATE;

	@PostConstruct
	public void updater() {
		setKey(WebPlatformConstants.Update.Menu.KEY);
		setName(WebPlatformConstants.Update.Menu.NAME);
		setSort(WebPlatformConstants.Update.Menu.SORT);
		setDescription(WebPlatformConstants.Update.Menu.DESCRIPTION);
	}

	@Override
	public void update() {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = null;
		try {
			resources = loader.getResources(MENU_IMPORT_UPDATE);
			for (Resource resource : resources) {
				try {
					InputStream in = resource.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					IOUtils.copy(in, baos);
					BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

					List<MenuCsv> menuCsvList = readCSVFile(reader);
					save(menuCsvList);

				} catch (Exception ex) {
					logger.error("Error reading the resource file: " + ex);
				}
			}
		} catch (IOException ex) {
			logger.error("Error reading the resource file: " + ex);
		}
	}

	public void save(List<MenuCsv> menuCsvList) throws Exception {

		// Dynamic Field

		Map<String, Object> enNameDynamicFieldProperties = new HashMap<String, Object>();
		enNameDynamicFieldProperties.put(DynamicField.ID, "menu_name_en");
		DynamicField enNameDynamicField = modelService.findOneEntityByProperties(enNameDynamicFieldProperties,
				DynamicField.class);

		if (enNameDynamicField == null) {
			enNameDynamicField = modelService.create(DynamicField.class);
			enNameDynamicField.setId("menu_name_en");
		}
		enNameDynamicField.setName("Name");
		enNameDynamicField.setRequired(true);
		enNameDynamicField.setRule(null);
		enNameDynamicField.setSort(0);
		enNameDynamicField.setType(DynamicFieldTypeEnum.TEXT);
		modelService.saveEntity(enNameDynamicField);

		Map<String, Object> cnNameDynamicFieldProperties = new HashMap<String, Object>();
		cnNameDynamicFieldProperties.put(DynamicField.ID, "menu_name_cn");
		DynamicField cnNameDynamicField = modelService.findOneEntityByProperties(cnNameDynamicFieldProperties,
				DynamicField.class);

		if (cnNameDynamicField == null) {
			cnNameDynamicField = modelService.create(DynamicField.class);
			cnNameDynamicField.setId("menu_name_cn");
		}
		cnNameDynamicField.setName("Name");
		cnNameDynamicField.setRequired(true);
		cnNameDynamicField.setRule(null);
		cnNameDynamicField.setSort(1);
		cnNameDynamicField.setType(DynamicFieldTypeEnum.TEXT);
		modelService.saveEntity(cnNameDynamicField);

		// Language

		Map<String, Object> enlanguageProperties = new HashMap<String, Object>();
		enlanguageProperties.put(Language.ID, "en");
		Language enLanguage = modelService.findOneEntityByProperties(enlanguageProperties, Language.class);

		Map<String, Object> cnlanguageProperties = new HashMap<String, Object>();
		cnlanguageProperties.put(Language.ID, "cn");
		Language cnLanguage = modelService.findOneEntityByProperties(cnlanguageProperties, Language.class);

		for (MenuCsv csv : menuCsvList) {

			// Menu

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Menu.ID, csv.getId());
			Menu menu = modelService.findOneEntityByProperties(properties, Menu.class);

			if (menu == null) {
				menu = modelService.create(Menu.class);
				menu.setId(csv.getId());
			} else {
				Hibernate.initialize(menu.getUserGroups());
				Hibernate.initialize(menu.getMenuFields());
			}

			menu.setSort(csv.getSort());
			menu.setIcon(csv.getIcon());
			menu.setPath(csv.getPath());
			if(StringUtils.isEmpty(csv.getTarget())) {
				menu.setTarget(MenuTargetTypeEnum.SELF);
			}
			else {
				menu.setTarget(MenuTargetTypeEnum.valueOf(csv.getTarget()));
			}
			menu.setEnabled(csv.isEnabled());

			if (StringUtils.isNotEmpty(csv.getParent())) {
				Map<String, Object> parentProperties = new HashMap<String, Object>();
				parentProperties.put(Menu.ID, csv.getParent());
				Menu parent = modelService.findOneEntityByProperties(parentProperties, Menu.class);

				if (parent == null) {
					logger.error("Parent not exists: " + csv.getParent());
				} else {
					menu.setParent(parent);
				}
			}

			boolean enCreate = true;
			boolean cnCreate = true;

			if (enLanguage != null) {
				for (int i = 0; i < menu.getMenuFields().size(); i++) {
					if (menu.getMenuFields().get(i).getId().equals(csv.getId() + "_name_en")
							&& menu.getMenuFields().get(i).getLanguage().getId().equals("en")) {
						menu.getMenuFields().get(i).setLabel("Name");
						menu.getMenuFields().get(i).setValue(csv.getName_en());
						enCreate = false;
					}
				}
			}
			if (cnLanguage != null) {
				for (int i = 0; i < menu.getMenuFields().size(); i++) {
					if (menu.getMenuFields().get(i).getId().equals(csv.getId() + "_name_cn")
							&& menu.getMenuFields().get(i).getLanguage().getId().equals("cn")) {
						menu.getMenuFields().get(i).setLabel("名称");
						menu.getMenuFields().get(i).setValue(csv.getName_cn());
						cnCreate = false;
					}
				}
			}

			modelService.saveEntity(menu);

			// User Group

			String[] userGroupIds = csv.getUserGroupIds().split(SPLITTER);
			for (int i = 0; i < userGroupIds.length; i++) {
				Map<String, Object> userGroupProperties = new HashMap<String, Object>();
				userGroupProperties.put(UserGroup.ID, userGroupIds[i]);
				UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, UserGroup.class);

				if (userGroup == null) {
					logger.error("UserGroup not exists: " + userGroupIds[i]);
				} else {
					menu.getUserGroups().add(userGroup);

					modelService.saveEntity(userGroup);
				}
			}

			// Menu Field

			if (enCreate) {
				MenuField menuField = modelService.create(MenuField.class);
				menuField.setId(csv.getId() + "_name_en");
				menuField.setDynamicField(enNameDynamicField);
				menuField.setLanguage(enLanguage);
				menuField.setLabel("Name");
				menuField.setValue(csv.getName_en());
				menuField.setMenu(menu);
				menu.getMenuFields().add(menuField);

				modelService.saveEntity(menuField);
			}
			if (cnCreate) {
				MenuField menuField = modelService.create(MenuField.class);
				menuField.setId(csv.getId() + "_name_cn");
				menuField.setDynamicField(cnNameDynamicField);
				menuField.setLanguage(cnLanguage);
				menuField.setLabel("名称");
				menuField.setValue(csv.getName_cn());
				menuField.setMenu(menu);
				menu.getMenuFields().add(menuField);

				modelService.saveEntity(menuField);
			}
		}

		modelService.saveAll();
	}

	public List<MenuCsv> readCSVFile(Reader reader) {
		ICsvBeanReader beanReader = null;

		List<MenuCsv> menuCsvList = new ArrayList<MenuCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			MenuCsv menuCsv;
			logger.info("Start import Menu Csv.");
			while ((menuCsv = beanReader.read(MenuCsv.class, header, processors)) != null) {
				logger.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), menuCsv);
				menuCsvList.add(menuCsv);
			}
			logger.info("Finished import Menu Csv.");
		} catch (FileNotFoundException ex) {
			logger.error("Could not find the CSV file: " + ex);
		} catch (IOException ex) {
			logger.error("Error reading the CSV file: " + ex);
		} finally {
			if (beanReader != null) {
				try {
					beanReader.close();
				} catch (IOException ex) {
					logger.error("Error closing the reader: " + ex);
				}
			}
		}
		return menuCsvList;
	}

	public CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(), // ID
				new ParseInt(), // sort
				new Optional(), // icon
				new Optional(), // path
				new Optional(), // target
				new ParseBool(), // enabled
				new Optional(), // parent
				new NotNull(), // name_en
				new NotNull(), // name_cn
				new Optional() // userGroupIds
		};

		return processors;
	}

}
