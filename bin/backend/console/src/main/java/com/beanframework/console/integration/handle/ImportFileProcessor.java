package com.beanframework.console.integration.handle;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.beanframework.console.registry.ImportListener;
import com.beanframework.console.registry.ImportListenerRegistry;
import com.beanframework.core.data.FileProcessor;

@Component
public class ImportFileProcessor implements FileProcessor {

	private static final String MSG = "%s received. Path: %s";

	@Autowired
	private ImportListenerRegistry importerRegistry;

	@Override
	public void process(Message<String> msg) throws Exception {
		String fileName = (String) msg.getHeaders().get(FileHeaders.FILENAME);
		File fileOriginalFile = (File) msg.getHeaders().get(FileHeaders.ORIGINAL_FILE);

		System.out.println(String.format(MSG, fileName, fileOriginalFile.getAbsolutePath()));

//		byte[] buffer = FileUtils.readFileToByteArray(fileOriginalFile);
//		Reader targetReader = new CharSequenceReader(new String(buffer));

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

			if (fileName.endsWith("_update") && entry.getKey().startsWith(fileName.toLowerCase())) {
				entry.getValue().updateByPath(fileOriginalFile.getAbsolutePath());
			}

			if (fileName.endsWith("_remove") && entry.getKey().startsWith(fileName.toLowerCase())) {
				entry.getValue().removeByPath(fileOriginalFile.getAbsolutePath());
			}
		}
	}
}
