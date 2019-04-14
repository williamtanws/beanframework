package com.beanframework.backoffice.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.core.data.ConfigurationDto;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.facade.ConfigurationFacade;
import com.beanframework.core.facade.UserFacade;
import com.beanframework.media.MediaConstants;
import com.beanframework.user.UserConstants;

@Controller
public class UserController {

	@Autowired
	private ConfigurationFacade configurationFacade;

	@Autowired
	private UserFacade userFacade;

	@Value(MediaConstants.MEDIA_LOCATION)
	public String MEDIA_LOCATION;

	@Value(UserConstants.USER_MEDIA_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@Value(BackofficeWebConstants.Configuration.DEFAULT_AVATAR)
	public String CONFIGURATION_DEFAULT_AVATAR;

	@GetMapping(value = UserConstants.PATH_USER_PROFILE_PICTURE, produces = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getImage(@RequestParam Map<String, Object> requestParams) throws Exception {

		UserDto userDto;

		if (requestParams.get("uuid") != null) {
			userDto = userFacade.findOneByUuid(UUID.fromString((String) requestParams.get("uuid")));
		} else {
			userDto = userFacade.getCurrentUser();
		}

		String type = (String) requestParams.get("type");

		if (StringUtils.isBlank(type) || (type.equals("original") == false && type.equals("thumbnail") == false)) {
			type = "thumbnail";
		}

		InputStream targetStream;
		File picture = new File(MEDIA_LOCATION, PROFILE_PICTURE_LOCATION + File.separator + userDto.getUuid() + File.separator + type + ".png");

		if (picture.exists()) {
			targetStream = new FileInputStream(picture);

			String mimeType = URLConnection.guessContentTypeFromName(picture.getName());

			return ResponseEntity.ok().contentType(MediaType.valueOf(mimeType)).body(IOUtils.toByteArray(targetStream));
		} else {

			ConfigurationDto configuration = configurationFacade.findOneDtoById(CONFIGURATION_DEFAULT_AVATAR);

			if (configuration == null) {
				return null;
			} else {

				ClassPathResource resource = new ClassPathResource(configuration.getValue());
				targetStream = resource.getInputStream();

				String mimeType = URLConnection.guessContentTypeFromName(picture.getName());

				return ResponseEntity.ok().contentType(MediaType.valueOf(mimeType)).body(IOUtils.toByteArray(targetStream));
			}
		}
	}
}
