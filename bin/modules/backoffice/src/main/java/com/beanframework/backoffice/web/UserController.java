package com.beanframework.backoffice.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.core.facade.ConfigurationFacade;
import com.beanframework.media.MediaConstants;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.User;

@PreAuthorize("isAuthenticated()")
@Controller
public class UserController {

	@Autowired
	private ConfigurationFacade configurationFacade;

	@Value(MediaConstants.MEDIA_LOCATION)
	public String MEDIA_LOCATION;

	@Value(UserConstants.USER_MEDIA_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@Value(BackofficeWebConstants.Configuration.DEFAULT_AVATAR)
	public String CONFIGURATION_DEFAULT_AVATAR;

	@GetMapping(value = UserConstants.PATH_USER_PROFILE_PICTURE, produces = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getImage(@RequestParam Map<String, Object> requestParams) throws Exception {

		UUID userUuid;

		if (requestParams.get("uuid") != null && StringUtils.isNotBlank(requestParams.get("uuid").toString())) {
			userUuid = UUID.fromString((String) requestParams.get("uuid"));
		} else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (auth != null) {

				User principal = (User) auth.getPrincipal();
				userUuid = principal.getUuid();
			} else {
				return getDefaultPicture();
			}
		}

		String type = (String) requestParams.get("type");

		if (StringUtils.isBlank(type) || (type.equals("original") == false && type.equals("thumbnail") == false)) {
			type = "thumbnail";
		}

		File picture = new File(MEDIA_LOCATION + File.separator + PROFILE_PICTURE_LOCATION, userUuid.toString() + File.separator + type + ".png");
		if (picture.exists()) {
			InputStream targetStream = new FileInputStream(picture);
			String mimeType = URLConnection.guessContentTypeFromName(picture.getName());

			return ResponseEntity.ok().contentType(MediaType.valueOf(mimeType)).body(IOUtils.toByteArray(targetStream));
		} else {
			return getDefaultPicture();
		}
	}
	
	private ResponseEntity<byte[]> getDefaultPicture() throws Exception {
		InputStream targetStream;
		
		String configurationValue = configurationFacade.get(CONFIGURATION_DEFAULT_AVATAR, null);

		if (configurationValue == null) {
			return null;
		} else {

			ClassPathResource resource = new ClassPathResource(configurationValue);
			targetStream = resource.getInputStream();

			String mimeType = URLConnection.guessContentTypeFromName(FilenameUtils.getExtension(configurationValue));
			
			if(StringUtils.isNotBlank(mimeType)) {
				return ResponseEntity.ok().contentType(MediaType.valueOf(mimeType)).body(IOUtils.toByteArray(targetStream));
			}
			else {
				return ResponseEntity.ok().body(IOUtils.toByteArray(targetStream));
			}
		}
	}
}
