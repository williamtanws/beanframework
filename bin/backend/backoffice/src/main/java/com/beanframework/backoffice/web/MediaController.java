package com.beanframework.backoffice.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.MediaWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.MediaDto;
import com.beanframework.core.facade.MediaFacade;
import com.beanframework.core.facade.MediaFacade.MediaPreAuthorizeEnum;
import com.beanframework.media.MediaConstants;
import com.beanframework.media.domain.Media;

@Controller
public class MediaController extends AbstractController {

	@Autowired
	private MediaFacade mediaFacade;

	@Value(MediaWebConstants.Path.MEDIA)
	private String PATH_MEDIA;

	@Value(MediaWebConstants.View.LIST)
	private String VIEW_MEDIA_LIST;

	@Value("${module.media.location.assets}")
	private String MEDIA_ASSETS;

	@Value(MediaConstants.MEDIA_LOCATION)
	private String MEDIA_LOCATION;

	@Value(MediaConstants.MEDIA_URL)
	public String MEDIA_URL;

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = MediaWebConstants.Path.MEDIA)
	public String list(@Valid @ModelAttribute(MediaWebConstants.ModelAttribute.MEDIA_DTO) MediaDto mediaDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (mediaDto.getUuid() != null) {

			MediaDto existsDto = mediaFacade.findOneByUuid(mediaDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(MediaWebConstants.ModelAttribute.MEDIA_DTO, existsDto);
			} else {
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_MEDIA_LIST;
	}

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = MediaWebConstants.Path.MEDIA, params = "create")
	public String createView(@Valid @ModelAttribute(MediaWebConstants.ModelAttribute.MEDIA_DTO) MediaDto mediaDto, Model model) throws Exception {

		mediaDto = mediaFacade.createDto();
		model.addAttribute(MediaWebConstants.ModelAttribute.MEDIA_DTO, mediaDto);
		model.addAttribute("create", true);

		return VIEW_MEDIA_LIST;
	}

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = MediaWebConstants.Path.MEDIA, params = "create")
	public RedirectView create(@Valid @ModelAttribute(MediaWebConstants.ModelAttribute.MEDIA_DTO) MediaDto mediaDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (mediaDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				mediaDto = mediaFacade.create(mediaDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(MediaDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(MediaDto.UUID, mediaDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MEDIA);
		return redirectView;
	}

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = MediaWebConstants.Path.MEDIA, params = "update")
	public RedirectView update(@Valid @ModelAttribute(MediaWebConstants.ModelAttribute.MEDIA_DTO) MediaDto mediaDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (mediaDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				mediaDto = mediaFacade.update(mediaDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(MediaDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(MediaDto.UUID, mediaDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MEDIA);
		return redirectView;
	}

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = MediaWebConstants.Path.MEDIA, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(MediaWebConstants.ModelAttribute.MEDIA_DTO) MediaDto mediaDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			mediaFacade.delete(mediaDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(MediaDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(MediaDto.UUID, mediaDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MEDIA);
		return redirectView;
	}

	@PreAuthorize(MediaPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = MediaWebConstants.Path.MEDIA, params = "download")
	public ResponseEntity<InputStreamResource> downloadFile(@RequestParam Map<String, Object> requestParams) throws Exception {

		String uuid = (String) requestParams.get("mediaUuid");
		if (StringUtils.isBlank(uuid)) {
			return new ResponseEntity<InputStreamResource>(HttpStatus.NOT_FOUND);
		}

		MediaDto mediaDto = mediaFacade.findOneByUuid(UUID.fromString(uuid));
		if (mediaDto == null) {
			return new ResponseEntity<InputStreamResource>(HttpStatus.NOT_FOUND);
		}

		File file = new File(MEDIA_LOCATION + File.separator + mediaDto.getFolder() + File.separator + mediaDto.getUuid().toString(), mediaDto.getFileName());
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName()).contentType(MediaType.valueOf(mediaDto.getFileType())).contentLength(file.length())
				.body(resource);
	}

	@GetMapping(value = { MediaWebConstants.Path.MEDIA + "/{uuid}/{fileName:.+}", MediaConstants.MEDIA_URL + "/{uuid}/{fileName:.+}" })
	public ResponseEntity<byte[]> media(@PathVariable String uuid, @PathVariable String fileName) throws Exception {

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Media.URL, MEDIA_URL + "/" + uuid + "/" + fileName);
		MediaDto mediaDto = mediaFacade.findOneProperties(properties);

		if (mediaDto == null) {
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}

		File file = new File(MEDIA_LOCATION + File.separator + mediaDto.getFolder() + File.separator + mediaDto.getUuid().toString(), mediaDto.getFileName());
		InputStream targetStream = new FileInputStream(file);

		return ResponseEntity.ok().contentType(MediaType.valueOf(mediaDto.getFileType())).body(IOUtils.toByteArray(targetStream));
	}
}
