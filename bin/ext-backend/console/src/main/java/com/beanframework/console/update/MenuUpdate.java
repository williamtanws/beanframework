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

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.domain.Updater;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.MenuCsv;
import com.beanframework.language.domain.Language;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuLang;
import com.beanframework.menu.service.MenuFacade;
import com.beanframework.user.domain.UserGroup;

public class MenuUpdate extends Updater {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MenuFacade menuFacade;

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

				} catch (IOException ex) {
					logger.error("Error reading the resource file: " + ex);
				}
			}
		} catch (IOException ex) {
			logger.error("Error reading the resource file: " + ex);
		}
	}

	public void save(List<MenuCsv> menuCsvList) {

		for (MenuCsv menuCsv : menuCsvList) {
			Menu menu = menuFacade.create();
			menu.setId(menuCsv.getId());
			menu.setSort(menuCsv.getSort());
			menu.setIcon(menuCsv.getIcon());
			menu.setPath(menuCsv.getPath());
			menu.setTarget(menuCsv.getTarget());
			menu.setEnabled(menuCsv.isEnabled());
			
			if(StringUtils.isNotEmpty(menuCsv.getParent())) {
				Menu parent = new Menu();
				parent.setId(menuCsv.getParent());
				menu.setParent(parent);
			}
			
			Language language = new Language();
			language.setId("en");
			MenuLang menuLang = new MenuLang();
			menuLang.setLanguage(language);
			menuLang.setName(menuCsv.getName_en());
			menu.getMenuLangs().add(menuLang);
			
			language = new Language();
			language.setId("cn");
			menuLang = new MenuLang();
			menuLang.setLanguage(language);
			menuLang.setName(menuCsv.getName_cn());
			menu.getMenuLangs().add(menuLang);
			
			menu.getUserGroups().clear();
			String[] userGroupIds = menuCsv.getUserGroupIds().split(SPLITTER);
			for (int i = 0; i < userGroupIds.length; i++) {
				UserGroup userGroup = new UserGroup();
				userGroup.setId(userGroupIds[i]);
				menu.getUserGroups().add(userGroup);
			}
			
			MapBindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(), Menu.class.getName());
			menuFacade.save(menu, bindingResult);

			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					logger.error(objectError.toString());
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
		final CellProcessor[] processors = new CellProcessor[] { 
				new UniqueHashCode(), // ID
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
