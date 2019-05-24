package com.beanframework.imex.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.imex.ImexConstants;
import com.beanframework.imex.domain.Imex;
import com.beanframework.imex.registry.ImportListener;
import com.beanframework.imex.registry.ImportListenerRegistry;
import com.beanframework.media.MediaConstants;
import com.beanframework.media.domain.Media;
import com.beanframework.media.service.MediaService;

@Service
public class ImexServiceImpl implements ImexService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ImexServiceImpl.class);

	@Autowired
	private ImportListenerRegistry importerRegistry;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Autowired
	private ModelService modelService;

	@Autowired
	private MediaService mediaService;

	@Autowired
	private List<ConverterMapping> converterMappings;

	@Autowired
	private EntityManager entityManager;

	@Value(MediaConstants.MEDIA_LOCATION)
	private String MEDIA_LOCATION;

	@Value(ImexConstants.IMEX_MEDIA_LOCATION)
	private String IMEX_MEDIA_LOCATION;

	@Value(MediaConstants.MEDIA_URL)
	private String MEDIA_URL;

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		List<Object[]> histories = modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Imex.class);
		return histories;
	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.countHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Imex.class);
	}

	@Transactional(readOnly = false)
	@Override
	public String[] importByListenerKeys(Set<String> keys) {
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
					boolean imported = importByKeysAndReader(keys, reader);
					if (imported) {
						LOGGER.info("Imported path: " + resource.getFile().getAbsolutePath());
						successMessages.append(localeMessageService.getMessage("module.console.platform.update.success", new Object[] { resource.getFile().getAbsolutePath() }) + "<br>");
					}
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
	public void importByFile(File file) throws Exception {
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
					boolean imported = importByKeysAndReader(null, reader);
					if (imported) {
						LOGGER.info("Imported path: " + resource.getFile().getAbsolutePath());
						successMessages.append(localeMessageService.getMessage("module.console.platform.update.success", new Object[] { resource.getFile().getAbsolutePath() }) + "<br>");
					}
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

	@Transactional(readOnly = false)
	@Override
	public String[] importByMultipartFiles(MultipartFile[] files) {
		// Messages
		StringBuilder successMessages = new StringBuilder();
		StringBuilder errorMessages = new StringBuilder();

		for (MultipartFile multipartFile : files) {
			if (StringUtils.isNotBlank(multipartFile.getOriginalFilename())) {

				try {

					String content = IOUtils.toString(multipartFile.getInputStream(), Charset.defaultCharset());

					boolean imported = importByKeysAndReader(null, new StringReader(content));

					if (imported) {
						LOGGER.info("Imported path: " + multipartFile.getResource().getFile().getAbsolutePath());
						successMessages
								.append(localeMessageService.getMessage("module.console.platform.update.success", new Object[] { multipartFile.getResource().getFile().getAbsolutePath() }) + "<br>");
					}
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

	@SuppressWarnings({ "unchecked", "resource" })
	private boolean importByKeysAndReader(Set<String> keys, Reader reader) throws Exception {
		boolean imported = false;

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

		for (Entry<String, ImportListener> entry : sortedImportListeners) {
			if ((keys == null && entry.getKey().equalsIgnoreCase(type)) || (keys.contains(entry.getKey()) && entry.getKey().equalsIgnoreCase(type))) {
				

				LOGGER.info("Import mode: " + mode.toUpperCase());
				LOGGER.info("Import type: " + type.toUpperCase());
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
				
				imported = true;
			}
		}

		return imported;
	}

	@Override
	public Media exportToCsv(Imex model) throws Exception {
		List<?> resultList = entityManager.createQuery(model.getQuery()).getResultList();

		final StringBuilder csv = new StringBuilder();
		csv.append(model.getHeader());
		for (final Object object : resultList) {
			final Object[] values = (Object[]) object;

			csv.append(System.getProperty("line.separator"));
			for (int i = 0; i < values.length; i++) {
				if (i != 0) {
					csv.append(model.getSeperator());
				}

				csv.append(StringUtils.stripToEmpty(values[i].toString()));
			}
		}

		if (StringUtils.isNotBlank(model.getDirectory())) {
			File directory = new File(model.getDirectory());
			FileUtils.forceMkdir(directory);

			File file = new File(directory, model.getFileName());
			FileUtils.write(file.getAbsoluteFile(), csv.toString(), "UTF-8", false);
		}

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Media.ID, model.getId().toString());
		Media media = modelService.findOneByProperties(properties, Media.class);

		if (media == null) {
			media = modelService.create(Media.class);
			media.setId(model.getId().toString());
		}

		media.setTitle(model.getFileName());
		media.setFileName(model.getFileName() + ".csv");
		media.setFileType("text/csv");
		media.setFolder(IMEX_MEDIA_LOCATION);
		media = modelService.saveEntity(media, Media.class);
		media = mediaService.storeData(media, csv.toString());

		if (model.getMedias().isEmpty()) {
			model.getMedias().add(media);
			modelService.saveEntityQuietly(model, Imex.class);
		}

		return media;
	}
}
