package com.beanframework.backoffice.api;

import static com.beanframework.filemanager.utils.RarUtils.unRarFile;
import static com.beanframework.filemanager.utils.TargzUtils.unTargzFile;
import static com.beanframework.filemanager.utils.ZipUtils.unZipFiles;
import static com.beanframework.filemanager.utils.ZipUtils.zipFiles;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beanframework.backoffice.WebFilemanagerConstants;

@RestController
public class FilemanagerResource {
	
	@Value(WebFilemanagerConstants.FILE_MANAGER_LOCATION)
	public String STORAGE;

	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.READ)
	@RequestMapping(WebFilemanagerConstants.Path.Api.ANGULARFILEMANAGER_LIST)
	public Object list(@RequestBody JSONObject json) throws ServletException {

		try {
			// 需�?显示的目录路径
			String path = json.getString("path");

			// 返回的结果集
			List<JSONObject> fileItems = new ArrayList<>();
			
			FileUtils.forceMkdir(new File(STORAGE));

			try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(STORAGE, path))) {

				String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat dt = new SimpleDateFormat(DATE_FORMAT);
				for (Path pathObj : directoryStream) {
					// 获�?�文件基本属性
					BasicFileAttributes attrs = Files.readAttributes(pathObj, BasicFileAttributes.class);

					// �?装返回JSON数�?�
					JSONObject fileItem = new JSONObject();
					fileItem.put("name", pathObj.getFileName().toString());
//					fileItem.put("rights", com.beanframework.filemanager.utils.FileUtils.getPermissions(pathObj)); // 文件�?��?
					// 文件�?��?
					fileItem.put("date", dt.format(new Date(attrs.lastModifiedTime().toMillis())));
					fileItem.put("size", attrs.size());
					fileItem.put("type", attrs.isDirectory() ? "dir" : "file");
					fileItems.add(fileItem);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", fileItems);
			return jsonObject;
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * 文件上传
	 */
	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.CREATE)
	@RequestMapping(WebFilemanagerConstants.Path.Api.ANGULARFILEMANAGER_UPLOAD)
	public Object upload(@RequestParam("destination") String destination, HttpServletRequest request) {

		try {
			// Servlet3.0方�?上传文件
			Collection<Part> parts = request.getParts();

			for (Part part : parts) {
				if (part.getContentType() != null) { // 忽略路径字段,�?�处�?�文件类型
					String path = STORAGE + destination;

					File f = new File(path, com.beanframework.filemanager.utils.FileUtils.getFileName(part.getHeader("content-disposition")));
					if (!com.beanframework.filemanager.utils.FileUtils.write(part.getInputStream(), f)) {
						throw new Exception("文件上传失败");
					}
				}
			}
			return success();
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * 文件下载/预览
	 */
	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.READ)
	@RequestMapping(WebFilemanagerConstants.Path.Api.ANGULARFILEMANAGER_PREVIEW)
	public void preview(HttpServletResponse response, String path) throws IOException {

		File file = new File(STORAGE, path);
		if (!file.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource Not Found");
			return;
		}

		/*
		 * 获�?�mimeType
		 */
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}

		response.setContentType(mimeType);
		response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", URLEncoder.encode(file.getName(), "UTF-8")));
		response.setContentLength((int) file.length());

		try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}

	/**
	 * 创建目录
	 */
	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.CREATE)
	@RequestMapping(WebFilemanagerConstants.Path.Api.ANGULARFILEMANAGER_CREATEFOLDER)
	public Object createFolder(@RequestBody JSONObject json) {
		try {
			String newPath = json.getString("newPath");
			File newDir = new File(STORAGE + newPath);
			if (!newDir.mkdir()) {
				throw new Exception("�?能创建目录: " + newPath);
			}
			return success();
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * 修改文件或目录�?��?
	 */
	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.UPDATE)
	@RequestMapping(WebFilemanagerConstants.Path.Api.ANGULARFILEMANAGER_CHANGEPERMISSIONS)
	public Object changePermissions(@RequestBody JSONObject json) {
		try {

			String perms = json.getString("perms"); // �?��?
			boolean recursive = json.getBoolean("recursive"); // �?目录是�?�生效

			JSONArray items = json.getJSONArray("items");
			for (int i = 0; i < items.size(); i++) {
				String path = items.getString(i);
				File f = new File(STORAGE, path);
				com.beanframework.filemanager.utils.FileUtils.setPermissions(f, perms, recursive); // 设置�?��?
			}
			return success();
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * �?制文件或目录
	 */
	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.CREATE)
	@RequestMapping(WebFilemanagerConstants.Path.Api.ANGULARFILEMANAGER_COPY)
	public Object copy(@RequestBody JSONObject json, HttpServletRequest request) {
		try {
			String newpath = json.getString("newPath");
			JSONArray items = json.getJSONArray("items");

			for (int i = 0; i < items.size(); i++) {
				String path = items.getString(i);

				File srcFile = new File(STORAGE, path);
				File destFile = new File(STORAGE + newpath, srcFile.getName());

				FileCopyUtils.copy(srcFile, destFile);
			}
			return success();
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * 移动文件或目录
	 */
	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.UPDATE)
	@RequestMapping(WebFilemanagerConstants.Path.Api.ANGULARFILEMANAGER_MOVE)
	public Object move(@RequestBody JSONObject json) {
		try {
			String newpath = json.getString("newPath");
			JSONArray items = json.getJSONArray("items");

			for (int i = 0; i < items.size(); i++) {
				String path = items.getString(i);

				File srcFile = new File(STORAGE, path);
				File destFile = new File(STORAGE + newpath, srcFile.getName());

				if (srcFile.isFile()) {
					FileUtils.moveFile(srcFile, destFile);
				} else {
					FileUtils.moveDirectory(srcFile, destFile);
				}
			}
			return success();
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * 删除文件或目录
	 */
	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.DELETE)
	@RequestMapping(WebFilemanagerConstants.Path.Api.ANGULARFILEMANAGER_REMOVE)
	public Object remove(@RequestBody JSONObject json) {
		try {
			JSONArray items = json.getJSONArray("items");
			for (int i = 0; i < items.size(); i++) {
				String path = items.getString(i);
				File srcFile = new File(STORAGE, path);
				if (!FileUtils.deleteQuietly(srcFile)) {
					throw new Exception("删除失败: " + srcFile.getAbsolutePath());
				}
			}
			return success();
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * �?命�??文件或目录
	 */
	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.UPDATE)
	@RequestMapping(WebFilemanagerConstants.Path.Api.ANGULARFILEMANAGER_RENAME)
	public Object rename(@RequestBody JSONObject json) {
		try {
			String path = json.getString("item");
			String newPath = json.getString("newItemPath");

			File srcFile = new File(STORAGE, path);
			File destFile = new File(STORAGE, newPath);
			if (srcFile.isFile()) {
				FileUtils.moveFile(srcFile, destFile);
			} else {
				FileUtils.moveDirectory(srcFile, destFile);
			}
			return success();
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * 查看文件内容,针对html�?txt等�?�编辑文件
	 */
	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.CREATE)
	@RequestMapping(WebFilemanagerConstants.Path.Api.ANGULARFILEMANAGER_GETCONTENT)
	public Object getContent(@RequestBody JSONObject json) {
		try {
			String path = json.getString("item");
			File srcFile = new File(STORAGE, path);

			String content = FileUtils.readFileToString(srcFile);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", content);
			return jsonObject;
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * 修改文件内容,针对html�?txt等�?�编辑文件
	 */
	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.UPDATE)
	@RequestMapping(WebFilemanagerConstants.Path.Api.ANGULARFILEMANAGER_EDIT)
	public Object edit(@RequestBody JSONObject json) {
		try {
			String path = json.getString("item");
			String content = json.getString("content");

			File srcFile = new File(STORAGE, path);
			FileUtils.writeStringToFile(srcFile, content);

			return success();
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * 文件压缩
	 */
	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.UPDATE)
	@RequestMapping(WebFilemanagerConstants.Path.Api.ANGULARFILEMANAGER_COMPRESS)
	public Object compress(@RequestBody JSONObject json) {
		try {
			String destination = json.getString("destination");
			String compressedFilename = json.getString("compressedFilename");
			JSONArray items = json.getJSONArray("items");
			List<File> files = new ArrayList<>();
			for (int i = 0; i < items.size(); i++) {
				File f = new File(STORAGE, items.getString(i));
				files.add(f);
			}

			File zip = new File(STORAGE + destination, compressedFilename);

			try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip))) {
				zipFiles(out, "", files.toArray(new File[files.size()]));
			}
			return success();
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * 文件解压
	 */
	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.CREATE)
	@RequestMapping(WebFilemanagerConstants.Path.Api.ANGULARFILEMANAGER_EXTRACT)
	public Object extract(@RequestBody JSONObject json) {
		try {
			String destination = json.getString("destination");
			String zipName = json.getString("item");
			String folderName = json.getString("folderName");
			File file = new File(STORAGE, zipName);

			String extension = com.beanframework.filemanager.utils.FileUtils.getExtension(zipName);
			switch (extension) {
			case ".zip":
				unZipFiles(file, STORAGE + destination, folderName);
				break;
			case ".gz":
				unTargzFile(file, STORAGE + destination);
				break;
			case ".rar":
				unRarFile(file, STORAGE + destination);
			}
			return success();
		} catch (Exception e) {
			return error(e.getMessage());
		}
	}

	private JSONObject error(String msg) {

		// { "result": { "success": false, "error": "msg" } }
		JSONObject result = new JSONObject();
		result.put("success", false);
		result.put("error", msg);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		return jsonObject;

	}

	private JSONObject success() {
		// { "result": { "success": true, "error": null } }
		JSONObject result = new JSONObject();
		result.put("success", true);
		result.put("error", null);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		return jsonObject;
	}
}