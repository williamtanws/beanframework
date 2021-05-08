package com.beanframework.console.api;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.common.data.TreeJson;
import com.beanframework.common.data.TreeJsonState;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.console.UpdateWebConstants;
import com.beanframework.imex.ImexConstants;

@RestController
public class UpdateResource {

	@Value(ImexConstants.IMEX_IMPORT_UPDATE_LOCATIONS)
	List<String> IMEX_IMPORT_LOCATIONS;
	
	@Autowired
	private LocaleMessageService localeMessageService;

	private List<String> getUpdateFolders(String location) throws IOException {
		List<String> folders = new ArrayList<String>();
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(location);

		for (Resource resource : resources) {
			File file = resource.getFile();
			String[] directories = file.list(new FilenameFilter() {
				@Override
				public boolean accept(File current, String name) {
					return new File(current, name).isDirectory();
				}
			});
			Arrays.sort(directories);
			for (int i = 0; i < directories.length; i++) {
				folders.add(directories[i]);
			}
		}

		return folders;
	}

	@RequestMapping(UpdateWebConstants.Path.Api.UPDATE_TREE)
	public List<TreeJson> list(Model model, @RequestParam Map<String, Object> requestParams) throws BusinessException, IOException {

		List<TreeJson> data = new ArrayList<TreeJson>();
		TreeJson parent = new TreeJson();
		parent.setId("all");
		parent.setText(localeMessageService.getMessage("module.common.all", "All"));
		parent.setChildren(new ArrayList<TreeJson>());
		TreeJsonState state = new TreeJsonState();
		state.setOpened(true);
		parent.setState(state);

		for (String location : IMEX_IMPORT_LOCATIONS) {
			TreeJson treeLocation = new TreeJson();
			treeLocation.setId(location);
			treeLocation.setText(location);
			treeLocation.setChildren(new ArrayList<TreeJson>());

			for (String folder : getUpdateFolders(location)) {
				TreeJson treeFolder = new TreeJson();
				treeFolder.setId(location + ";" + folder);
				treeFolder.setText(folder);
				treeLocation.getChildren().add(treeFolder);
			}

			parent.getChildren().add(treeLocation);
		}

		data.add(parent);

		return data;
	}
}
