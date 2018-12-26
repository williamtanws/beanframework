package com.beanframework.console.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.common.controller.AbstractCommonController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.domain.ConfigurationSpecification;
import com.beanframework.console.WebConfigurationConstants;
import com.beanframework.console.WebConsoleConstants;
import com.beanframework.console.domain.ConfigurationSearch;

@Controller
public class ConfigurationController extends AbstractCommonController {

	@Autowired
	private ModelService modelService;

	@Value(WebConfigurationConstants.Path.CONFIGURATION)
	private String PATH_CONFIGURATION;

	@Value(WebConfigurationConstants.View.LIST)
	private String VIEW_CONFIGURATION_LIST;

	@Value(WebConfigurationConstants.LIST_SIZE)
	private int MODULE_CONFIGURATION_LIST_SIZE;

	private Page<Configuration> getPagination(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
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

		Page<Configuration> pagination = modelService.findDtoPage(ConfigurationSpecification.findByCriteria(configuration),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties),
				Configuration.class);

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
	public Configuration populateConfigurationCreate(HttpServletRequest request) throws Exception {
		return modelService.create(Configuration.class);
	}

	@ModelAttribute(WebConfigurationConstants.ModelAttribute.UPDATE)
	public Configuration populateConfigurationForm(HttpServletRequest request) throws Exception {
		return modelService.create(Configuration.class);
	}

	@ModelAttribute(WebConfigurationConstants.ModelAttribute.SEARCH)
	public ConfigurationSearch populateConfigurationSearch(HttpServletRequest request) {
		return new ConfigurationSearch();
	}

	@GetMapping(value = WebConfigurationConstants.Path.CONFIGURATION)
	public String list(
			@ModelAttribute(WebConfigurationConstants.ModelAttribute.SEARCH) ConfigurationSearch configurationSearch,
			@ModelAttribute(WebConfigurationConstants.ModelAttribute.UPDATE) Configuration configurationUpdate,
			Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(WebConsoleConstants.PAGINATION, getPagination(model, requestParams));

		if (configurationUpdate.getUuid() != null) {

			Configuration existingConfiguration = modelService.findOneEntityByUuid(configurationUpdate.getUuid(),
					Configuration.class);
			existingConfiguration = modelService.getDto(existingConfiguration, Configuration.class);

			if (existingConfiguration != null) {
				model.addAttribute(WebConfigurationConstants.ModelAttribute.UPDATE, existingConfiguration);
			} else {
				configurationUpdate.setUuid(null);
				addErrorMessage(model, WebConsoleConstants.Locale.RECORD_UUID_NOT_FOUND);
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
			try {
				modelService.saveDto(configurationCreate, Configuration.class);

				addSuccessMessage(redirectAttributes, WebConsoleConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Configuration.class, e.getMessage(), bindingResult, redirectAttributes);
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
			try {
				modelService.saveDto(configurationUpdate, Configuration.class);

				addSuccessMessage(redirectAttributes, WebConsoleConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Configuration.class, e.getMessage(), bindingResult, redirectAttributes);
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

		try {
			modelService.delete(configurationUpdate.getUuid(), Configuration.class);

			addSuccessMessage(redirectAttributes, WebConsoleConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(Configuration.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(WebConfigurationConstants.ModelAttribute.UPDATE, configurationUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, configurationSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CONFIGURATION);
		return redirectView;

	}
}
