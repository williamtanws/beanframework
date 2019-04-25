package com.beanframework.console.service;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.console.registry.ImportListener;
import com.beanframework.console.registry.ImportListenerRegistry;
import com.beanframework.console.web.PlatformUpdateController;

@Service
public class PlatformServiceImpl implements PlatformService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PlatformUpdateController.class);

	@Autowired
	private ImportListenerRegistry importerRegistry;

	@Transactional(readOnly = false)
	@Override
	public String[] update(Set<String> keys) {

		StringBuilder successMessages = new StringBuilder();
		StringBuilder errorMessages = new StringBuilder();

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

		for (Entry<String, ImportListener> entry : sortedImportListeners) {
			if (keys.contains(entry.getKey())) {
				try {
					entry.getValue().update();
					entry.getValue().remove();
					successMessages.append(entry.getValue().getName() + " is updated successfully. <br>");
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error(e.getMessage(), e);
					errorMessages.append(entry.getValue().getName() + " is updated failed. Reason: " + e.getMessage() + " <br><br>");
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
	public String[] importFiles(MultipartFile[] files) {
		StringBuilder successMessages = new StringBuilder();
		StringBuilder errorMessages = new StringBuilder();

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

		for (MultipartFile multipartFile : files) {
			if (multipartFile.isEmpty() == false) {

				String fileName = multipartFile.getOriginalFilename();

				boolean updated = false;
				for (Entry<String, ImportListener> entry : sortedImportListeners) {

					try {
						if (fileName.endsWith("_update.csv")) {
							if (fileName.toLowerCase().startsWith(entry.getKey())) {
								String content = IOUtils.toString(multipartFile.getInputStream(), Charset.defaultCharset());
								entry.getValue().updateByContent(content);
								successMessages.append(entry.getValue().getName() + " is updated successfully. <br>");
								updated = true;
							}
						} else if (fileName.endsWith("_remove.csv")) {
							if (fileName.toLowerCase().startsWith(entry.getKey())) {
								String content = IOUtils.toString(multipartFile.getInputStream(), Charset.defaultCharset());
								entry.getValue().removeByContent(content);
								successMessages.append(entry.getValue().getName() + " is updated successfully. <br>");
								updated = true;
							}
						}

					} catch (Exception e) {
						errorMessages.append(entry.getValue().getName() + " is updated failed. Reason: " + e.getMessage() + " <br>");
					}
				}

				if (updated == false) {
					errorMessages.append(fileName + " is updated failed. Reason: File Name not valid. <br>");
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

		for (Entry<String, ImportListener> entry : sortedImportListeners) {

			if (file.getName().endsWith("_update.csv")) {
				if (file.getName().toLowerCase().startsWith(entry.getKey()))
					entry.getValue().updateByPath(file.getAbsolutePath());
			}

			if (file.getName().endsWith("_remove.csv")) {
				if (file.getName().toLowerCase().startsWith(entry.getKey()))
					entry.getValue().removeByPath(file.getAbsolutePath());
			}
		}
	}

}
