package com.beanframework.console.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationFacade;
import com.beanframework.console.WebConfigurationConstants;
import com.beanframework.console.WebConsoleConstants;
import com.beanframework.console.domain.ConfigurationSearch;

@Controller
public class ConfigurationController {

	@Autowired
	private ConfigurationFacade configurationFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(WebConfigurationConstants.Path.CONFIGURATION)
	private String PATH_CONFIGURATION;

	@Value(WebConfigurationConstants.View.LIST)
	private String VIEW_CONFIGURATION_LIST;

	@Value(WebConfigurationConstants.LIST_SIZE)
	private int MODULE_CONFIGURATION_LIST_SIZE;

	private Page<Configuration> getPagination(Model model, @RequestParam Map<String, Object> requestParams) {
		int page = ParamUtils.parseInt(requestParams.get(WebConsoleConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebConsoleConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_CONFIGURATION_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebConsoleConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isEmpty(propertiesStr) ? null
				: propertiesStr.split(WebConsoleConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebConsoleConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isEmpty(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		ConfigurationSearch configurationSearch = (ConfigurationSearch) model.asMap()
				.get(WebConfigurationConstants.ModelAttribute.SEARCH);

		Configuration configuration = new Configuration();
		configuration.setId(configurationSearch.getIdSearch());

		if (properties == null) {
			properties = new String[1];
			properties[0] = Configuration.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<Configuration> pagination = configurationFacade.page(configuration, page, size, direction, properties);

		model.addAttribute(WebConsoleConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebConsoleConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, ConfigurationSearch configurationSearch) {
		int page = ParamUtils.parseInt(requestParams.get(WebConsoleConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebConsoleConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_CONFIGURATION_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebConsoleConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(WebConsoleConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(WebConsoleConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(WebConsoleConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(WebConsoleConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(WebConsoleConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute(ConfigurationSearch.ID_SEARCH, configurationSearch.getIdSearch());
		redirectAttributes.addFlashAttribute(WebConfigurationConstants.ModelAttribute.SEARCH, configurationSearch);

		return redirectAttributes;
	}

	@ModelAttribute(WebConfigurationConstants.ModelAttribute.CREATE)
	public Configuration populateConfigurationCreate(HttpServletRequest request) {
		return configurationFacade.create();
	}

	@ModelAttribute(WebConfigurationConstants.ModelAttribute.UPDATE)
	public Configuration populateConfigurationForm(HttpServletRequest request) {
		return configurationFacade.create();
	}

	@ModelAttribute(WebConfigurationConstants.ModelAttribute.SEARCH)
	public ConfigurationSearch populateConfigurationSearch(HttpServletRequest request) {
		return new ConfigurationSearch();
	}

	@GetMapping(value = WebConfigurationConstants.Path.CONFIGURATION)
	public String list(
			@ModelAttribute(WebConfigurationConstants.ModelAttribute.SEARCH) ConfigurationSearch configurationSearch,
			@ModelAttribute(WebConfigurationConstants.ModelAttribute.UPDATE) Configuration configurationUpdate,
			Model model, @RequestParam Map<String, Object> requestParams) {

		model.addAttribute(WebConsoleConstants.PAGINATION, getPagination(model, requestParams));

		if (configurationUpdate.getUuid() != null) {
			Configuration existingConfiguration = configurationFacade.findByUuid(configurationUpdate.getUuid());
			if (existingConfiguration != null) {
				model.addAttribute(WebConfigurationConstants.ModelAttribute.UPDATE, existingConfiguration);
			} else {
				configurationUpdate.setUuid(null);
				model.addAttribute(WebConsoleConstants.Model.ERROR,
						localeMessageService.getMessage(WebConsoleConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}

		return VIEW_CONFIGURATION_LIST;
	}

	@PostMapping(value = WebConfigurationConstants.Path.CONFIGURATION, params = "create")
	public RedirectView create(
			@ModelAttribute(WebConfigurationConstants.ModelAttribute.SEARCH) ConfigurationSearch configurationSearch,
			@ModelAttribute(WebConfigurationConstants.ModelAttribute.CREATE) Configuration configurationCreate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (configurationCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {
			configurationCreate = configurationFacade.save(configurationCreate, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebConsoleConstants.Locale.SAVE_SUCCESS));
			}
		}

		redirectAttributes.addAttribute(Configuration.UUID, configurationCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, configurationSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CONFIGURATION);
		return redirectView;
	}

	@PostMapping(value = WebConfigurationConstants.Path.CONFIGURATION, params = "update")
	public RedirectView update(
			@ModelAttribute(WebConfigurationConstants.ModelAttribute.SEARCH) ConfigurationSearch configurationSearch,
			@ModelAttribute(WebConfigurationConstants.ModelAttribute.UPDATE) Configuration configurationUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (configurationUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			configurationUpdate = configurationFacade.save(configurationUpdate, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebConsoleConstants.Locale.SAVE_SUCCESS));
			}
		}

		redirectAttributes.addAttribute(Configuration.UUID, configurationUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, configurationSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CONFIGURATION);
		return redirectView;
	}

	@PostMapping(value = WebConfigurationConstants.Path.CONFIGURATION, params = "delete")
	public RedirectView delete(
			@ModelAttribute(WebConfigurationConstants.ModelAttribute.SEARCH) ConfigurationSearch configurationSearch,
			@ModelAttribute(WebConfigurationConstants.ModelAttribute.UPDATE) Configuration configurationUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		configurationFacade.delete(configurationUpdate.getUuid(), bindingResult);

		if (bindingResult.hasErrors()) {

			StringBuilder errorMessage = new StringBuilder();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				if (errorMessage.length() != 0) {
					errorMessage.append("<br>");
				}
				errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
			}

			redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.ERROR, errorMessage.toString());
			redirectAttributes.addFlashAttribute(WebConfigurationConstants.ModelAttribute.UPDATE, configurationUpdate);
		} else {

			redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.SUCCESS,
					localeMessageService.getMessage(WebConsoleConstants.Locale.DELETE_SUCCESS));
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, configurationSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CONFIGURATION);
		return redirectView;

	}
}