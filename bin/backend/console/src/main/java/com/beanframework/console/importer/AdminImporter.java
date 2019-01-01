package com.beanframework.console.importer;

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
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformUpdateConstants;
import com.beanframework.console.csv.AdminCsv;
import com.beanframework.console.registry.Importer;
import com.beanframework.user.utils.PasswordUtils;

public class AdminImporter extends Importer {
	private static Logger LOGGER = LoggerFactory.getLogger(AdminImporter.class);

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.admin}")
	private String IMPORT_UPDATE;
	
	@Value("${module.console.import.remove.admin}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(WebPlatformUpdateConstants.Importer.Admin.KEY);
		setName(WebPlatformUpdateConstants.Importer.Admin.NAME);
		setSort(WebPlatformUpdateConstants.Importer.Admin.SORT);
		setDescription(WebPlatformUpdateConstants.Importer.Admin.DESCRIPTION);
	}

	@Override
	public void update() throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(IMPORT_UPDATE);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<AdminCsv> adminCsvList = readCSVFile(reader, AdminCsv.getUpdateProcessors());
			save(adminCsvList);
		}
	}
	
	@Override
	public void remove() throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(IMPORT_REMOVE);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<AdminCsv> adminCsvList = readCSVFile(reader, AdminCsv.getRemoveProcessors());
			remove(adminCsvList);
		}
	}

	public List<AdminCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<AdminCsv> csvList = new ArrayList<AdminCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			AdminCsv csv;
			LOGGER.info("Start import "+WebPlatformUpdateConstants.Importer.Admin.NAME);
			while ((csv = beanReader.read(AdminCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import "+WebPlatformUpdateConstants.Importer.Admin.NAME);
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
		return csvList;
	}
	
	public void save(List<AdminCsv> csvList) throws Exception {

		for (AdminCsv csv : csvList) {

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Admin.ID, csv.getId());

			Admin admin = modelService.findOneEntityByProperties(properties, Admin.class);

			if (admin == null) {
				admin = modelService.create(Admin.class);
				admin.setId(csv.getId());
			} else {
				Hibernate.initialize(admin.getFields());
			}

			if (StringUtils.isNotBlank(csv.getPassword())) {
				admin.setPassword(PasswordUtils.encode(csv.getPassword()));
			}
			admin.setAccountNonExpired(csv.isAccountNonExpired());
			admin.setAccountNonLocked(csv.isAccountNonLocked());
			admin.setCredentialsNonExpired(csv.isCredentialsNonExpired());
			admin.setEnabled(csv.isEnabled());			
			
			modelService.saveEntity(admin, Admin.class);
		}
	}
	
	public void remove(List<AdminCsv> csvList) throws Exception {
		for (AdminCsv csv : csvList) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Admin.ID, csv.getId());
			Admin model = modelService.findOneEntityByProperties(properties, Admin.class);
			modelService.deleteByEntity(model, Admin.class);
		}
	}

}
