package com.beanframework.backoffice.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.UserWebConstants;
import com.beanframework.backoffice.UserWebConstants.UserPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.facade.ConfigurationFacade;
import com.beanframework.core.facade.UserFacade;
import com.beanframework.media.MediaConstants;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.User;

@Controller
public class UserController extends AbstractController {

	@Autowired
	private ConfigurationFacade configurationFacade;

	@Value(MediaConstants.MEDIA_LOCATION)
	public String MEDIA_LOCATION;

	@Value(UserConstants.USER_MEDIA_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@Value(BackofficeWebConstants.Configuration.DEFAULT_AVATAR)
	public String CONFIGURATION_DEFAULT_AVATAR;

	@Autowired
	private UserFacade userFacade;

	@Value(UserWebConstants.Path.USER)
	private String PATH_USER;

	@Value(UserWebConstants.Path.USER_FORM)
	private String PATH_USER_FORM;

	@Value(UserWebConstants.View.USER)
	private String VIEW_USER;

	@Value(UserWebConstants.View.USER_FORM)
	private String VIEW_USER_FORM;

	@PreAuthorize(UserPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = UserWebConstants.Path.USER)
	public String page(@Valid @ModelAttribute(UserWebConstants.ModelAttribute.USER_DTO) UserDto userDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_USER;
	}

	@PreAuthorize(UserPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = UserWebConstants.Path.USER_FORM)
	public String form(@Valid @ModelAttribute(UserWebConstants.ModelAttribute.USER_DTO) UserDto userDto, Model model) throws Exception {

		if (userDto.getUuid() != null) {
			userDto = userFacade.findOneByUuid(userDto.getUuid());
		} else {
			userDto = userFacade.createDto();
		}
		model.addAttribute(UserWebConstants.ModelAttribute.USER_DTO, userDto);

		return VIEW_USER_FORM;
	}

	@PreAuthorize(UserPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = UserWebConstants.Path.USER_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(UserWebConstants.ModelAttribute.USER_DTO) UserDto userDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (userDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				userDto = userFacade.create(userDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, userDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USER_FORM);
		return redirectView;
	}

	@PreAuthorize(UserPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = UserWebConstants.Path.USER_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(UserWebConstants.ModelAttribute.USER_DTO) UserDto userDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (userDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				userDto = userFacade.update(userDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, userDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USER_FORM);
		return redirectView;
	}

	@PreAuthorize(UserPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = UserWebConstants.Path.USER_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(UserWebConstants.ModelAttribute.USER_DTO) UserDto dto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				userFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USER_FORM);
		return redirectView;

	}

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
				
				if(userUuid == null) {
					return getDefaultPicture();
				}
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

		String configurationValue = configurationFacade.get(CONFIGURATION_DEFAULT_AVATAR, "/static/core/img/avatar.png");

		if (configurationValue == null) {
			return null;
		} else {

			ClassPathResource resource = new ClassPathResource(configurationValue);
			targetStream = resource.getInputStream();

			String mimeType = URLConnection.guessContentTypeFromName(FilenameUtils.getExtension(configurationValue));

			if (StringUtils.isNotBlank(mimeType)) {
				return ResponseEntity.ok().contentType(MediaType.valueOf(mimeType)).body(IOUtils.toByteArray(targetStream));
			} else {
				return ResponseEntity.ok().body(IOUtils.toByteArray(targetStream));
			}
		}
	}
}
