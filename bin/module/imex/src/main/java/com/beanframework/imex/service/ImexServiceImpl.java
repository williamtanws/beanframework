package com.beanframework.imex.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Method;
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
		return importByKeysAndReader(keys, "classpath*:import/**/*.csv");
	}

	@Transactional(readOnly = false)
	@Override
	public void importByFile(File file) throws Exception {

		String[] message = importByKeysAndReader(null, file.getAbsolutePath());

		if (message[0] != null)
			LOGGER.info("Success: " + message[0].toString());

		if (message[1] != null)
			LOGGER.info("Error: " + message[1].toString());
	}

	@Transactional(readOnly = false)
	@Override
	public String[] importByMultipartFiles(MultipartFile[] files) {
		String[] messages = new String[2];

		for (MultipartFile multipartFile : files) {
			try {
				String[] returnMessage = importByKeysAndReader(null, multipartFile.getResource().getFile().getAbsolutePath());
				if (returnMessage[0] != null) {
					if (messages[0] == null) {
						messages[0] = returnMessage[0];
					} else {
						messages[0] = returnMessage[0].concat(returnMessage[0]);
					}
				}
				if (returnMessage[1] != null) {
					if (messages[1] == null) {
						messages[1] = returnMessage[1];
					} else {
						messages[1] = returnMessage[1].concat(returnMessage[1]);
					}
				}
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}

		return messages;
	}

	@SuppressWarnings({ "unchecked", "resource" })
	private String[] importByKeysAndReader(Set<String> keys, String location) {

		// Messages
		StringBuilder successMessages = new StringBuilder();
		StringBuilder errorMessages = new StringBuilder();
		StringBuilder loggingMessage = new StringBuilder();
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
		
		Resource[] resources = null;
		try {
			PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
			resources = loader.getResources(location);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}

		for (Entry<String, ImportListener> entry : sortedImportListeners) {
			if ((keys == null) || (keys != null && keys.contains(entry.getKey()))) {

				for (Resource resource : resources) {
					try {
						InputStream in = resource.getInputStream();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						IOUtils.copy(in, baos);
						BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));
						
						ICsvBeanReader beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);
						final String[] header = beanReader.getHeader(true);

						String mode = header[0].trim().replace("  ", " ").split(" ")[0];
						String type = header[0].trim().replace("  ", " ").split(" ")[1];

						if (type.equalsIgnoreCase(entry.getValue().getType())) {

							loggingMessage.append("Import mode=" + mode.toUpperCase() + ", type=" + type.toUpperCase());

							CellProcessor[] processors = null;
							if (mode.equalsIgnoreCase("INSERT") || mode.equalsIgnoreCase("UPDATE") || mode.equalsIgnoreCase("INSERT_UPDATE")) {
								Method method = entry.getValue().getClassCsv().getMethod("getUpdateProcessors", new Class[] {});
								processors = (CellProcessor[]) method.invoke(entry.getValue().getClassCsv(), new Object[] {});

							} else if (mode.equalsIgnoreCase("REMOVE")) {
								Method method = entry.getValue().getClassCsv().getMethod("getRemoveProcessors", new Class[] {});
								processors = (CellProcessor[]) method.invoke(entry.getValue().getClassCsv(), new Object[] {});
							}
							
							if (processors == null) {
								throw new Exception("Cannot find CellProcessor[] for " + entry.getValue().getClassCsv());
							}

							Object csv;
							while ((csv = beanReader.read(entry.getValue().getClassCsv(), header, processors)) != null) {

								if (entry.getValue().isCustomImport()) {
									imported = entry.getValue().customImport(csv);
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

									if (entity == null) {
										throw new Exception("Cannot find EntityCsvConverter bean for " + entry.getValue().getClassCsv().getSimpleName());
									}
									
									Class<?> classEntity = entry.getValue().getClassEntity();

									if (mode.equalsIgnoreCase("INSERT")) {
										if (((GenericEntity) entity).getUuid() == null) {
											modelService.saveEntity(entity, classEntity);
											imported = true;
										}

									} else if (mode.equalsIgnoreCase("UPDATE")) {
										if (((GenericEntity) entity).getUuid() != null) {
											modelService.saveEntity(entity, classEntity);
											imported = true;
										}

									} else if (mode.equalsIgnoreCase("INSERT_UPDATE")) {
										modelService.saveEntity(entity, classEntity);
										imported = true;

									} else if (mode.equalsIgnoreCase("REMOVE")) {
										GenericEntity genericEntity = (GenericEntity) entity;
										if (genericEntity.getUuid() != null) {
											modelService.deleteEntity(genericEntity, classEntity);
											imported = true;
										}
									}
								}

								if (imported) {
									loggingMessage.append("Imported line: lineNo=" + beanReader.getLineNumber() + ", rowNo=" + beanReader.getRowNumber() + ", " + csv);
									loggingMessage.append(System.getProperty("line.separator"));
								}
							}
							successMessages.append(localeMessageService.getMessage("module.console.platform.update.success", new Object[] { resource.getFile().getPath() }) + "<br>");
						}
					} catch (Exception e) {
						e.printStackTrace();
						LOGGER.error(e.getMessage(), e);
						try {
							errorMessages.append(localeMessageService.getMessage("module.console.platform.import.fail", new Object[] { resource.getFile().getPath(), e.getMessage() }) + "<br><br>");
						} catch (IOException e1) {
							e1.printStackTrace();
							LOGGER.error(e.getMessage(), e);
						}
					}

				}
			}
		}

		if (imported) {
			LOGGER.info(loggingMessage.toString());
		}

		String[] messages = new String[2];
		messages[0] = successMessages.toString();
		messages[1] = errorMessages.toString();

		return messages;
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
