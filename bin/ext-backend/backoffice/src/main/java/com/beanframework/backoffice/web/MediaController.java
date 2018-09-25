package com.beanframework.backoffice.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebMediaConstants;
import com.beanframework.backoffice.domain.MediaSearch;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.media.domain.Media;
import com.beanframework.media.service.MediaFacade;

@Controller
public class MediaController {

	@Autowired
	private MediaFacade mediaFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(WebMediaConstants.Path.MEDIA)
	private String PATH_MULTIMEDIA;

	@Value(WebMediaConstants.View.LIST)
	private String VIEW_MULTIMEDIA_LIST;

	@Value(WebMediaConstants.LIST_SIZE)
	private int MODULE_MULTIMEDIA_LIST_SIZE;

	private Page<Media> getPagination(Model model, @RequestParam Map<String, Object> requestParams) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_MULTIMEDIA_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isEmpty(propertiesStr) ? null
				: propertiesStr.split(WebBackofficeConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isEmpty(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		MediaSearch mediaSearch = (MediaSearch) model.asMap().get(WebMediaConstants.ModelAttribute.SEARCH);

		Media media = new Media();
		media.setId(mediaSearch.getIdSearch());

		if (properties == null) {
			properties = new String[1];
			properties[0] = Media.CREATED_BY;
			direction = Sort.Direction.DESC;
		}

		Page<Media> pagination = mediaFacade.page(media, page, size, direction, properties);

		model.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, MediaSearch mediaSearch) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_MULTIMEDIA_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute(MediaSearch.ID_SEARCH, mediaSearch.getIdSearch());
		redirectAttributes.addFlashAttribute(WebMediaConstants.ModelAttribute.SEARCH, mediaSearch);

		return redirectAttributes;
	}

	@ModelAttribute(WebMediaConstants.ModelAttribute.CREATE)
	public Media populateMediaCreate(HttpServletRequest request) {
		return mediaFacade.create();
	}

	@ModelAttribute(WebMediaConstants.ModelAttribute.UPDATE)
	public Media populateMediaForm(HttpServletRequest request) {
		return mediaFacade.create();
	}

	@ModelAttribute(WebMediaConstants.ModelAttribute.SEARCH)
	public MediaSearch populateMediaSearch(HttpServletRequest request) {
		return new MediaSearch();
	}

	@PreAuthorize(WebMediaConstants.PreAuthorize.READ)
	@GetMapping(value = WebMediaConstants.Path.MEDIA)
	public String list(@ModelAttribute(WebMediaConstants.ModelAttribute.SEARCH) MediaSearch mediaSearch,
			@ModelAttribute(WebMediaConstants.ModelAttribute.UPDATE) Media mediaUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(model, requestParams));

		if (mediaUpdate.getUuid() != null) {
			Media existingMedia = mediaFacade.findByUuid(mediaUpdate.getUuid());
			if (existingMedia != null) {
				model.addAttribute(WebMediaConstants.ModelAttribute.UPDATE, existingMedia);
			} else {
				mediaUpdate.setUuid(null);
				model.addAttribute(WebBackofficeConstants.Model.ERROR,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}

		return VIEW_MULTIMEDIA_LIST;
	}

	@PreAuthorize(WebMediaConstants.PreAuthorize.CREATE)
	@PostMapping(value = WebMediaConstants.Path.MEDIA, params = "create")
	public RedirectView create(
			@ModelAttribute(WebMediaConstants.ModelAttribute.SEARCH) MediaSearch mediaSearch,
			@ModelAttribute(WebMediaConstants.ModelAttribute.CREATE) Media mediaCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (mediaCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {
			mediaCreate = mediaFacade.save(mediaCreate, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
			}
		}

		redirectAttributes.addAttribute(Media.UUID, mediaCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, mediaSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MULTIMEDIA);
		return redirectView;
	}

	@PreAuthorize(WebMediaConstants.PreAuthorize.UPDATE)
	@PostMapping(value = WebMediaConstants.Path.MEDIA, params = "update")
	public RedirectView update(
			@ModelAttribute(WebMediaConstants.ModelAttribute.SEARCH) MediaSearch mediaSearch,
			@ModelAttribute(WebMediaConstants.ModelAttribute.UPDATE) Media mediaUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (mediaUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			mediaUpdate = mediaFacade.save(mediaUpdate, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
			}
		}

		redirectAttributes.addAttribute(Media.UUID, mediaUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, mediaSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MULTIMEDIA);
		return redirectView;
	}

	@PreAuthorize(WebMediaConstants.PreAuthorize.DELETE)
	@PostMapping(value = WebMediaConstants.Path.MEDIA, params = "delete")
	public RedirectView delete(
			@ModelAttribute(WebMediaConstants.ModelAttribute.SEARCH) MediaSearch mediaSearch,
			@ModelAttribute(WebMediaConstants.ModelAttribute.UPDATE) Media mediaUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors() == false) {
			mediaFacade.delete(mediaUpdate.getUuid(), bindingResult);
		}

		if (bindingResult.hasErrors()) {

			StringBuilder errorMessage = new StringBuilder();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				if (errorMessage.length() != 0) {
					errorMessage.append("<br>");
				}
				errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
			}

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());
			redirectAttributes.addAttribute(Media.UUID, mediaUpdate.getUuid());
		} else {

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
					localeMessageService.getMessage(WebBackofficeConstants.Locale.DELETE_SUCCESS));
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, mediaSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MULTIMEDIA);
		return redirectView;

	}
}
