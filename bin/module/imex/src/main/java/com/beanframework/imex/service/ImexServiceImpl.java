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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.imex.ImexConstants;
import com.beanframework.imex.ImexType;
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

	@Transactional
	@Override
	public String[] importByListenerKeys(Set<String> keys) {
		return importByKeysAndReader(keys, "classpath*:import/**/*.csv");
	}

	@Transactional
	@Override
	public void importByFile(File file) throws Exception {

		importByKeysAndReader(null, file.getAbsolutePath());
	}

	@Transactional
	@Override
	public String[] importByMultipartFiles(MultipartFile[] files) {

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

		// Messages
		StringBuilder successMessages = new StringBuilder();
		StringBuilder errorMessages = new StringBuilder();

		for (MultipartFile multipartFile : files) {
			if (StringUtils.isNotBlank(multipartFile.getOriginalFilename())) {

				for (Entry<String, ImportListener> entry : sortedImportListeners) {

					String content = null;
					try {
						content = IOUtils.toString(multipartFile.getInputStream(), Charset.defaultCharset());
					} catch (IOException e) {
						e.printStackTrace();
						LOGGER.error(e.getMessage(), e);
					}
					Reader reader = new StringReader(content);

					importCsv(multipartFile.getOriginalFilename(), reader, entry.getValue(), successMessages, errorMessages);
				}
			}
		}

		String[] messages = new String[2];
		messages[0] = successMessages.toString();
		messages[1] = errorMessages.toString();

		return messages;
	}

	@Transactional
	@Override
	public String[] importByQuery(String importName, String query) {

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

		// Messages
		StringBuilder successMessages = new StringBuilder();
		StringBuilder errorMessages = new StringBuilder();

		for (Entry<String, ImportListener> entry : sortedImportListeners) {

			Reader reader = new StringReader(query);
			importCsv(importName, reader, entry.getValue(), successMessages, errorMessages);
		}

		String[] messages = new String[2];
		messages[0] = successMessages.toString();
		messages[1] = errorMessages.toString();

		return messages;
	}

	private String[] importByKeysAndReader(Set<String> keys, String location) {

		// Messages
		StringBuilder successMessages = new StringBuilder();
		StringBuilder errorMessages = new StringBuilder();

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

					BufferedReader reader = null;
					String importName = null;
					try {
						InputStream in = resource.getInputStream();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						IOUtils.copy(in, baos);
						reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));
						importName = resource.getFilename();
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

					importCsv(importName, reader, entry.getValue(), successMessages, errorMessages);
				}
			}
		}

		String[] messages = new String[2];
		messages[0] = successMessages.toString();
		messages[1] = errorMessages.toString();

		return messages;
	}

	@SuppressWarnings({ "resource", "unchecked" })
	private void importCsv(String importName, Reader reader, ImportListener listener, StringBuilder successMessages, StringBuilder errorMessages) {
		try {
			ICsvBeanReader beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);
			final String[] header = beanReader.getHeader(true);

			String mode = header[0].trim().replace("  ", " ").split(" ")[0];
			String type = header[0].trim().replace("  ", " ").split(" ")[1];

			if (type.equalsIgnoreCase(listener.getType())) {

				LOGGER.info("Import mode=" + mode.toUpperCase() + ", type=" + type.toUpperCase());

				CellProcessor[] processors = null;
				if (mode.equalsIgnoreCase("INSERT") || mode.equalsIgnoreCase("UPDATE") || mode.equalsIgnoreCase("INSERT_UPDATE")) {
					Method method = listener.getClassCsv().getMethod("getUpdateProcessors", new Class[] {});
					processors = (CellProcessor[]) method.invoke(listener.getClassCsv(), new Object[] {});

				} else if (mode.equalsIgnoreCase("REMOVE")) {
					Method method = listener.getClassCsv().getMethod("getRemoveProcessors", new Class[] {});
					processors = (CellProcessor[]) method.invoke(listener.getClassCsv(), new Object[] {});
				}

				if (processors == null) {
					throw new Exception("Cannot find CellProcessor[] for " + listener.getClassCsv());
				}

				Object csv;
				while ((csv = beanReader.read(listener.getClassCsv(), header, processors)) != null) {

					boolean imported = false;

					if (listener.isCustomImport()) {
						imported = listener.customImport(csv);
					} else {

						Object entity = null;
						for (ConverterMapping converterMapping : converterMappings) {
							if (converterMapping.getConverter() instanceof EntityCsvConverter) {
								EntityCsvConverter<Object, ?> entityCsvConverter = (EntityCsvConverter<Object, ?>) converterMapping.getConverter();
								if (converterMapping.getTypeCode().equals(listener.getClassCsv().getSimpleName())) {
									entity = entityCsvConverter.convert(csv);
								}
							}
						}

						if (entity == null) {
							throw new Exception("Cannot find EntityCsvConverter bean for " + listener.getClassCsv().getSimpleName());
						}

						Class<?> classEntity = listener.getClassEntity();

						if (mode.equalsIgnoreCase("INSERT")) {
							if (((GenericEntity) entity).getUuid() == null) {
								modelService.saveEntity(entity);
								imported = true;
							}

						} else if (mode.equalsIgnoreCase("UPDATE")) {
							if (((GenericEntity) entity).getUuid() != null) {
								modelService.saveEntity(entity);
								imported = true;
							}

						} else if (mode.equalsIgnoreCase("INSERT_UPDATE")) {
							modelService.saveEntity(entity);
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
						LOGGER.info("Imported line: lineNo=" + beanReader.getLineNumber() + ", rowNo=" + beanReader.getRowNumber() + ", " + csv);
					}
				}
				successMessages.append(localeMessageService.getMessage("module.console.platform.update.success", new Object[] { importName }) + "<br>");

			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
			errorMessages.append(localeMessageService.getMessage("module.console.platform.import.fail", new Object[] { importName, e.getMessage() }) + "<br><br>");
		}
	}

	@Override
	public void importExportMedia(Imex model) throws Exception {

		String csv = null;
		if (model.getType() == ImexType.IMPORT) {
			String errorMessage = importByQuery(model.getId(), model.getQuery())[1];
			if(StringUtils.isNotBlank(errorMessage)) {
				throw new Exception(errorMessage);
			}
			csv = model.getQuery();
		} else if (model.getType() == ImexType.EXPORT) {

			List<?> resultList = entityManager.createQuery(model.getQuery()).getResultList();

			final StringBuilder csvBuilder = new StringBuilder();
			csvBuilder.append(model.getHeader());
			for (final Object object : resultList) {
				final Object[] values = (Object[]) object;

				csvBuilder.append(System.getProperty("line.separator"));
				for (int i = 0; i < values.length; i++) {
					if (i != 0) {
						csvBuilder.append(model.getSeperator());
					}

					if (values[i] != null)
						csvBuilder.append(values[i].toString());
				}
			}

			if (StringUtils.isNotBlank(model.getDirectory())) {
				File directory = new File(model.getDirectory());
				FileUtils.forceMkdir(directory);

				File file = new File(directory, model.getFileName());
				FileUtils.write(file.getAbsoluteFile(), csvBuilder.toString(), "UTF-8", false);
			}

			csv = csvBuilder.toString();
		}

		if (csv != null) {
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
			media = modelService.saveEntity(media);
			media = mediaService.storeData(media, csv);

			if (model.getMedias().isEmpty()) {
				model.getMedias().add(media);
				modelService.saveEntityQuietly(model, Imex.class);
			}
		}

	}
}
