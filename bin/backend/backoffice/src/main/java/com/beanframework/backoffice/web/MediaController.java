package com.beanframework.backoffice.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.MediaWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.MediaDto;
import com.beanframework.core.facade.MediaFacade;

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

	@GetMapping(value = MediaWebConstants.Path.MEDIA)
	public String list(@ModelAttribute(MediaWebConstants.ModelAttribute.MEDIA_DTO) MediaDto mediaDto, Model model) throws Exception {
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

	@GetMapping(value = MediaWebConstants.Path.MEDIA, params = "create")
	public String createView(@ModelAttribute(MediaWebConstants.ModelAttribute.MEDIA_DTO) MediaDto mediaDto, Model model) throws Exception {

		mediaDto = mediaFacade.createDto();
		model.addAttribute(MediaWebConstants.ModelAttribute.MEDIA_DTO, mediaDto);
		model.addAttribute("create", true);

		return VIEW_MEDIA_LIST;
	}

	@PostMapping(value = MediaWebConstants.Path.MEDIA, params = "create")
	public RedirectView create(@ModelAttribute(MediaWebConstants.ModelAttribute.MEDIA_DTO) MediaDto mediaDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
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

	@PostMapping(value = MediaWebConstants.Path.MEDIA, params = "update")
	public RedirectView update(@ModelAttribute(MediaWebConstants.ModelAttribute.MEDIA_DTO) MediaDto mediaDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
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

	@PostMapping(value = MediaWebConstants.Path.MEDIA, params = "delete")
	public RedirectView delete(@ModelAttribute(MediaWebConstants.ModelAttribute.MEDIA_DTO) MediaDto mediaDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

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

	@GetMapping(value = MediaWebConstants.Path.MEDIA_BROWSE)
	@ResponseBody
	public String browse(Model model, @RequestParam Map<String, Object> allRequestParams) throws Exception {
		return "window.opener.CKEDITOR.tools.callFunction( funcNum, fileUrl [, data] );";
	}

	@PostMapping(value = MediaWebConstants.Path.MEDIA_UPLOAD)
	@ResponseBody
	public String upload(Model model, @RequestParam Map<String, Object> allRequestParams, MultipartFile file) throws Exception {

		MediaDto mediaDto = mediaFacade.createByMultipartFile(file, MEDIA_ASSETS);
		String filePath = mediaDto.getUrl() + "/" + file.getOriginalFilename();
		return "{\"location\":\"" + filePath + "\"}";
	}

	@GetMapping(value = MediaWebConstants.Path.MEDIA_PUBLIC + "/{uuid}/{fileName}")
	public ResponseEntity<byte[]> media(@PathVariable String uuid, @PathVariable String fileName) throws Exception {

		MediaDto mediaDto = mediaFacade.findOneByUuid(UUID.fromString(uuid));

		File mediaFile = new File(mediaDto.getLocation(), fileName);

		InputStream targetStream = new FileInputStream(mediaFile);

		return ResponseEntity.ok().contentType(MediaType.valueOf(mediaDto.getFileType())).body(IOUtils.toByteArray(targetStream));
	}
}
