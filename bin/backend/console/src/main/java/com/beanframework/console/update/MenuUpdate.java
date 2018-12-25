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
import java.util.Iterator;
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
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.Updater;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.MenuCsv;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuNavigation;
import com.beanframework.menu.domain.MenuTargetTypeEnum;
import com.beanframework.user.domain.UserGroup;

public class MenuUpdate extends Updater {
	private static Logger LOGGER = LoggerFactory.getLogger(MenuUpdate.class);

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
	public void update() throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(MENU_IMPORT_UPDATE);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<MenuCsv> menuCsvList = readCSVFile(reader);
			save(menuCsvList);
		}
		modelService.clearCache(MenuNavigation.class);
	}

	public void save(List<MenuCsv> menuCsvList) throws Exception {

		for (MenuCsv csv : menuCsvList) {

			// Menu

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Menu.ID, csv.getId());
			Menu menu = modelService.findOneEntityByProperties(properties, Menu.class);

			if (menu == null) {
				menu = modelService.create(Menu.class);
				menu.setId(csv.getId());
			} else {
				Hibernate.initialize(menu.getParent());
				Hibernate.initialize(menu.getUserGroups());
				Hibernate.initialize(menu.getMenuFields());
			}

			menu.setSort(csv.getSort());
			menu.setIcon(csv.getIcon());
			menu.setPath(csv.getPath());
			if (StringUtils.isEmpty(csv.getTarget())) {
				menu.setTarget(MenuTargetTypeEnum.SELF);
			} else {
				menu.setTarget(MenuTargetTypeEnum.valueOf(csv.getTarget()));
			}
			menu.setEnabled(csv.isEnabled());

			modelService.saveEntity(menu, Menu.class);

			// Menu Field

			// UserRight Field

			if (csv.getDynamicField() != null) {
				String[] dynamicFields = csv.getDynamicField().split(";");
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split("=")[0];
					String value = dynamicField.split("=")[1];
					for (int i = 0; i < menu.getMenuFields().size(); i++) {
						if (menu.getMenuFields().get(i).getId().equals(menu.getId() + "_" + dynamicFieldId)) {
							menu.getMenuFields().get(i).setValue(value);
						}
					}
				}
			}

			modelService.saveEntity(menu, Menu.class);

			// Old Parent

			if (menu.getParent() != null) {
				Map<String, Object> parentProperties = new HashMap<String, Object>();
				parentProperties.put(Menu.UUID, menu.getParent().getUuid());
				Menu oldParent = modelService.findOneEntityByProperties(parentProperties, Menu.class);

				if (oldParent != null) {

					Iterator<Menu> iterator = oldParent.getChilds().iterator();

					while (iterator.hasNext()) {
						if (iterator.next().getUuid().equals(menu.getUuid())) {
							iterator.remove();
						}
					}
					modelService.saveEntity(oldParent, Menu.class);
				}
			}

			// New Parent

			if (StringUtils.isNotEmpty(csv.getParent())) {
				Map<String, Object> parentProperties = new HashMap<String, Object>();
				parentProperties.put(Menu.ID, csv.getParent());
				Menu parent = modelService.findOneEntityByProperties(parentProperties, Menu.class);

				if (parent == null) {
					LOGGER.error("Parent not exists: " + csv.getParent());
				} else {
					Hibernate.initialize(parent.getChilds());

					boolean addChild = true;
					for (Menu child : parent.getChilds()) {
						if (child.getUuid().equals(menu.getUuid())) {
							addChild = false;
						}
					}
					if (addChild) {
						menu.setParent(parent);
						parent.getChilds().add(menu);
						modelService.saveEntity(parent, Menu.class);
					}
				}
			}

			// User Group

			if (csv.getUserGroupIds() != null) {
				String[] userGroupIds = csv.getUserGroupIds().split(SPLITTER);
				for (int i = 0; i < userGroupIds.length; i++) {
					Map<String, Object> userGroupProperties = new HashMap<String, Object>();
					userGroupProperties.put(UserGroup.ID, userGroupIds[i]);
					UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, UserGroup.class);

					if (userGroup == null) {
						LOGGER.error("UserGroup not exists: " + userGroupIds[i]);
					} else {
						menu.getUserGroups().add(userGroup);

						modelService.saveEntity(userGroup, UserGroup.class);
					}
				}
			}
		}

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
			LOGGER.info("Start import Menu Csv.");
			while ((menuCsv = beanReader.read(MenuCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), menuCsv);
				menuCsvList.add(menuCsv);
			}
			LOGGER.info("Finished import Menu Csv.");
		} catch (FileNotFoundException ex) {
			LOGGER.error("Could not find the CSV file: " + ex);
		} catch (IOException ex) {
			LOGGER.error("Error reading the CSV file: " + ex);
		} finally {
			if (beanReader != null) {
				try {
					beanReader.close();
				} catch (IOException ex) {
					LOGGER.error("Error closing the reader: " + ex);
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
				new Optional(), // userGroupIds
				new Optional() // dynamicField
		};
		return processors;
	}
}
