package com.beanframework.console.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.console.registry.ImportListenerRegistry;
import com.beanframework.console.web.PlatformUpdateController;

@Service
public class PlatformServiceImpl implements PlatformService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PlatformUpdateController.class);

	@Autowired
	private ImportListenerRegistry importerRegistry;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Autowired
	private ModelService modelService;

	@Autowired
	protected List<ConverterMapping> converterMappings;

	@Transactional(readOnly = false)
	@Override
	public String[] update(Set<String> keys) {
		// Messages
		StringBuilder successMessages = new StringBuilder();
		StringBuilder errorMessages = new StringBuilder();

		try {
			// Retrieve all the import csv files
			PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
			Resource[] resources = loader.getResources("classpath*:import/**/*.csv");
			for (Resource resource : resources) {
				InputStream in = resource.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				IOUtils.copy(in, baos);
				BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

				try {
					LOGGER.info("Import path: " + resource.getFile().getAbsolutePath());
					readCsvFile(keys, reader);
					successMessages.append(localeMessageService.getMessage("module.console.platform.update.success", new Object[] { resource.getFile().getAbsolutePath() }) + "<br>");
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					errorMessages.append(localeMessageService.getMessage("module.console.platform.update.fail", new Object[] { resource.getFile().getAbsolutePath(), e.getMessage() }) + "<br><br>");
				}

			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

		String[] messages = new String[2];
		messages[0] = successMessages.toString();
		messages[1] = errorMessages.toString();

		return messages;
	}

	@Transactional(readOnly = false)
	@Override
	public String[] importFiles(MultipartFile[] files) {
		// Messages
		StringBuilder successMessages = new StringBuilder();
		StringBuilder errorMessages = new StringBuilder();

		for (MultipartFile multipartFile : files) {
			if (StringUtils.isNotBlank(multipartFile.getOriginalFilename())) {

				try {

					String content = IOUtils.toString(multipartFile.getInputStream(), Charset.defaultCharset());
					LOGGER.info("Import path: " + multipartFile.getResource().getFile().getAbsolutePath());
					readCsvFile(null, new StringReader(content));
					successMessages
							.append(localeMessageService.getMessage("module.console.platform.update.success", new Object[] { multipartFile.getResource().getFile().getAbsolutePath() }) + "<br>");
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					try {
						errorMessages
								.append(localeMessageService.getMessage("module.console.platform.import.fail", new Object[] { multipartFile.getResource().getFile().getAbsolutePath(), e.getMessage() })
										+ "<br><br>");
					} catch (IOException e1) {
						LOGGER.error(e1.getMessage(), e1);
					}
				}
			}
		}

		String[] messages = new String[2];
		messages[0] = successMessages.toString();
		messages[1] = errorMessages.toString();

		return messages;
	}

	@Transactional(readOnly = false)
	@Override
	public void importFile(File file) throws Exception {
		// Messages
		StringBuilder successMessages = new StringBuilder();
		StringBuilder errorMessages = new StringBuilder();

		try {
			// Retrieve all the import csv files
			PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
			Resource[] resources = loader.getResources(file.getAbsolutePath());
			for (Resource resource : resources) {
				InputStream in = resource.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				IOUtils.copy(in, baos);
				BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

				try {
					LOGGER.info("Import path: " + resource.getFile().getAbsolutePath());
					readCsvFile(null, reader);
					successMessages.append(localeMessageService.getMessage("module.console.platform.update.success", new Object[] { resource.getFile().getAbsolutePath() }) + "<br>");
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					errorMessages.append(localeMessageService.getMessage("module.console.platform.import.fail", new Object[] { resource.getFile().getAbsolutePath(), e.getMessage() }) + "<br><br>");
				}

			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

		if (successMessages.length() > 0)
			LOGGER.info("Success: " + successMessages.toString());

		if (errorMessages.length() > 0)
			LOGGER.info("Error: " + errorMessages.toString());
	}

	@SuppressWarnings({ "unchecked", "resource" })
	private void readCsvFile(Set<String> keys, Reader reader) throws Exception {

		// Sort Import Listener
		Set<Entry<String, ImportListener>> importListeners = importerRegistry.getListeners().entrySet();
		List<Entry<String, ImportListener>> sortedImportListeners = new LinkedList<Entry<String, ImportListener>>(importListeners);
		Collections.sort(sortedImportListeners, new Comparator<Entry<String, ImportListener>>() {
			@Override
			public int compare(Entry<String, ImportListener> ele1, Entry<String, ImportListener> ele2) {
				Integer sort1 = ele1.getValue().getSort();
				Integer sort2 = ele2.getValue().getSort();
				return sort1.compareTo(sort2);
			}
		});

		ICsvBeanReader beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);
		final String[] header = beanReader.getHeader(true);

		String mode = header[0].trim().replace("  ", " ").split(" ")[0];
		String type = header[0].trim().replace("  ", " ").split(" ")[1];

		LOGGER.info("Import mode: " + mode.toUpperCase());
		LOGGER.info("Import type: " + type.toUpperCase());

		for (Entry<String, ImportListener> entry : sortedImportListeners) {
			if (keys.contains(entry.getKey()) && entry.getKey().equalsIgnoreCase(type)) {
				LOGGER.info("Import name: " + entry.getValue().getName());

				CellProcessor[] processors = null;
				if (mode.equalsIgnoreCase("INSERT") || mode.equalsIgnoreCase("UPDATE") || mode.equalsIgnoreCase("INSERT_UPDATE")) {
					Method method = entry.getValue().getClassCsv().getMethod("getUpdateProcessors", new Class[] {});
					processors = (CellProcessor[]) method.invoke(entry.getValue().getClassCsv(), new Object[] {});

				} else if (mode.equalsIgnoreCase("REMOVE")) {
					Method method = entry.getValue().getClassCsv().getMethod("getRemoveProcessors", new Class[] {});
					processors = (CellProcessor[]) method.invoke(entry.getValue().getClassCsv(), new Object[] {});
				}
				LOGGER.info("Import csv class: " + entry.getValue().getClassCsv().getName());
				LOGGER.info("Import entity class: " + entry.getValue().getClassEntity().getName());

				for (ConverterMapping converterMapping : converterMappings) {
					if (converterMapping.getConverter() instanceof EntityCsvConverter) {
						EntityCsvConverter<?, ?> entityCsvConverter = (EntityCsvConverter<?, ?>) converterMapping.getConverter();
						if (converterMapping.getTypeCode().equals(entry.getValue().getClassCsv().getSimpleName())) {
							LOGGER.info("Import entity csv converter class: " + entityCsvConverter.getClass().getName());
						}
					}
				}

				Object csv;
				while ((csv = beanReader.read(entry.getValue().getClassCsv(), header, processors)) != null) {
					LOGGER.info("Import line: lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);

					if (entry.getValue().isCustomImport()) {
						entry.getValue().customImport(csv);
					} else {

						Object entity = null;
						for (ConverterMapping converterMapping : converterMappings) {
							if (converterMapping.getConverter() instanceof EntityCsvConverter) {
								EntityCsvConverter<Object, ?> entityCsvConverter = (EntityCsvConverter<Object, ?>) converterMapping.getConverter();
								if (converterMapping.getTypeCode().equals(entry.getValue().getClassCsv().getSimpleName())) {
									entity = entityCsvConverter.convert(csv);
								}
							}
						}

						if (mode.equalsIgnoreCase("INSERT")) {
							if (((GenericEntity) entity).getUuid() == null)
								modelService.saveEntity(entity, entry.getValue().getClassEntity());

						} else if (mode.equalsIgnoreCase("UPDATE")) {
							if (((GenericEntity) entity).getUuid() != null)
								modelService.saveEntity(entity, entry.getValue().getClassEntity());

						} else if (mode.equalsIgnoreCase("INSERT_UPDATE")) {
							modelService.saveEntity(entity, entry.getValue().getClassEntity());

						} else if (mode.equalsIgnoreCase("REMOVE")) {
							GenericEntity genericEntity = (GenericEntity) entity;
							if (genericEntity.getUuid() != null) {
								modelService.deleteEntity(genericEntity, entry.getValue().getClassEntity());
							}
						}
					}

				}
			}
		}
	}

}
