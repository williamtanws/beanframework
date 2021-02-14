package com.beanframework.backoffice;

public class FilemanagerWebConstants {

	public interface Path {

		public static final String FILE_MANAGER = "${path.filemanager}";
		public static final String ANGULARFILEMANAGER = "${path.filemanager.angularfilemanager}";
		public static final String TEMPLATES_PAGE = "${path.filemanager.angularfilemanager.templates.page}";

		public interface Api {
			public static final String FILE_MANAGER = "${path.api.filemanager}";
			public static final String ANGULARFILEMANAGER = "${path.api.filemanager.angularfilemanager}";
			public static final String ANGULARFILEMANAGER_LIST = "${path.api.filemanager.angularfilemanager}";
			public static final String ANGULARFILEMANAGER_UPLOAD = "${path.api.filemanager.angularfilemanager.upload}";
			public static final String ANGULARFILEMANAGER_PREVIEW = "${path.api.filemanager.angularfilemanager.preview}";
			public static final String ANGULARFILEMANAGER_CREATEFOLDER = "${path.api.filemanager.angularfilemanager.createFolder}";
			public static final String ANGULARFILEMANAGER_CHANGEPERMISSIONS = "${path.api.filemanager.angularfilemanager.changePermissions}";
			public static final String ANGULARFILEMANAGER_COPY = "${path.api.filemanager.angularfilemanager.copy}";
			public static final String ANGULARFILEMANAGER_MOVE = "${path.api.filemanager.angularfilemanager.move}";
			public static final String ANGULARFILEMANAGER_REMOVE = "${path.api.filemanager.angularfilemanager.remove}";
			public static final String ANGULARFILEMANAGER_RENAME = "${path.api.filemanager.angularfilemanager.rename}";
			public static final String ANGULARFILEMANAGER_GETCONTENT = "${path.api.filemanager.angularfilemanager.getContent}";
			public static final String ANGULARFILEMANAGER_EDIT = "${path.api.filemanager.angularfilemanager.edit}";
			public static final String ANGULARFILEMANAGER_COMPRESS = "${path.api.filemanager.angularfilemanager.compress}";
			public static final String ANGULARFILEMANAGER_EXTRACT = "${path.api.filemanager.angularfilemanager.extract}";
		}
	}

	public interface View {
		public static final String CONTAINER = "${view.filemanager.filemanagercontainer}";
		public static final String ANGULARFILEMANAGER = "${view.filemanager.angularfilemanager}";
		public static final String TEMPLATES = "${view.filemanager.angularfilemanager.templates}";
	}

	public static final String FILE_MANAGER_LOCATION = "${module.filemanager.location}";
}
