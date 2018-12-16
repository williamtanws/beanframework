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

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.Updater;
import com.beanframework.common.exception.ModelSavingException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.UserPermissionCsv;
import com.beanframework.language.domain.Language;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionLang;

public class UserPermissionUpdate extends Updater {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.userpermission}")
	private String USERRIGHT_IMPORT_UPDATE;

	@PostConstruct
	public void updater() {
		setKey(WebPlatformConstants.Update.UserPermission.KEY);
		setName(WebPlatformConstants.Update.UserPermission.NAME);
		setSort(WebPlatformConstants.Update.UserPermission.SORT);
		setDescription(WebPlatformConstants.Update.UserPermission.DESCRIPTION);
	}

	@Override
	public void update() {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = null;
		try {
			resources = loader.getResources(USERRIGHT_IMPORT_UPDATE);
			for (Resource resource : resources) {
				try {
					InputStream in = resource.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					IOUtils.copy(in, baos);
					BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

					List<UserPermissionCsv> userPermissionCsvList = readCSVFile(reader);
					save(userPermissionCsvList);

				} catch (IOException ex) {
					logger.error("Error reading the resource file: " + ex);
				}
			}
		} catch (IOException ex) {
			logger.error("Error reading the resource file: " + ex);
		}
	}

	public void save(List<UserPermissionCsv> userPermissionCsvList) {
		
		for (UserPermissionCsv csv : userPermissionCsvList) {
			
			Map<String, Object> userPermissionProperties = new HashMap<String, Object>();
			userPermissionProperties.put(UserPermission.ID, csv.getId());
			
			UserPermission userPermission = modelService.findOneEntityByProperties(userPermissionProperties, UserPermission.class);
			
			if(userPermission == null) {
				userPermission = modelService.create(UserPermission.class);
				userPermission.setId(csv.getId());
			}
			userPermission.setSort(csv.getSort());
			
			Map<String, Object> enlanguageProperties = new HashMap<String, Object>();
			enlanguageProperties.put(Language.ID, "en");
			
			Language enLanguage = modelService.findOneEntityByProperties(enlanguageProperties, Language.class);
			
			if(enLanguage != null) {
				boolean create = true;
				for (int i = 0; i < userPermission.getUserPermissionLangs().size(); i++) {
					if (userPermission.getUserPermissionLangs().get(i).getLanguage().getId().equals("en")) {
						userPermission.getUserPermissionLangs().get(i).setName(csv.getName_en());
						create = false;
					}
				}
				
				if(create) {
					UserPermissionLang userPermissionLang = modelService.create(UserPermissionLang.class);
					userPermissionLang.setLanguage(enLanguage);
					userPermissionLang.setName(csv.getName_en());
					userPermission.getUserPermissionLangs().add(userPermissionLang);
				}
			}
			
			Map<String, Object> cnlanguageProperties = new HashMap<String, Object>();
			cnlanguageProperties.put(Language.ID, "cn");
			
			Language cnLanguage = modelService.findOneEntityByProperties(cnlanguageProperties, Language.class);
			
			if(cnLanguage != null) {
				boolean create = true;
				for (int i = 0; i < userPermission.getUserPermissionLangs().size(); i++) {
					if (userPermission.getUserPermissionLangs().get(i).getLanguage().getId().equals("cn")) {
						userPermission.getUserPermissionLangs().get(i).setName(csv.getName_cn());
						create = false;
					}
				}
				
				if(create) {
					UserPermissionLang userPermissionLang = modelService.create(UserPermissionLang.class);
					userPermissionLang.setLanguage(cnLanguage);
					userPermissionLang.setName(csv.getName_cn());
					userPermission.getUserPermissionLangs().add(userPermissionLang);
				}
			}
			
			try {
				modelService.save(userPermission);
			} catch (ModelSavingException e) {
				logger.error(e.getMessage());
			}
		}
	}

	public List<UserPermissionCsv> readCSVFile(Reader reader) {
		ICsvBeanReader beanReader = null;

		List<UserPermissionCsv> permissionCsvList = new ArrayList<UserPermissionCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			UserPermissionCsv permissionCsv;
			logger.info("Start import UserPermission Csv.");
			while ((permissionCsv = beanReader.read(UserPermissionCsv.class, header, processors)) != null) {
				logger.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(),
						permissionCsv);
				permissionCsvList.add(permissionCsv);
			}
			logger.info("Finished import UserPermission Csv.");
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
		return permissionCsvList;
	}

	public CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { 
				new UniqueHashCode(), // id
				new ParseInt(), // sort
				new NotNull(), // name_en
				new NotNull() // name_cn
		};

		return processors;
	}

}
