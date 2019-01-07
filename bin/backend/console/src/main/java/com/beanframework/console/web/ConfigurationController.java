package com.beanframework.console.web;

import java.util.List;
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

import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationFacade;
import com.beanframework.console.ConfigurationWebConstants;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.console.data.ConfigurationSearch;
import com.beanframework.console.data.ConfigurationSpecification;

@Controller
public class ConfigurationController extends AbstractController {

	@Autowired
	private ConfigurationFacade configurationFacade;

	@Value(ConfigurationWebConstants.Path.CONFIGURATION)
	private String PATH_CONFIGURATION;

	@Value(ConfigurationWebConstants.View.LIST)
	private String VIEW_CONFIGURATION_LIST;

	@Value(ConfigurationWebConstants.LIST_SIZE)
	private int MODULE_CONFIGURATION_LIST_SIZE;

	private Page<Configuration> getPagination(ConfigurationSearch configurationSearch, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(ConsoleWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(ConsoleWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_CONFIGURATION_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(ConsoleWebConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null
				: propertiesStr.split(ConsoleWebConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(ConsoleWebConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		if (properties == null) {
			properties = new String[1];
			properties[0] = Configuration.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<Configuration> pagination = configurationFacade.findDtoPage(ConfigurationSpecification.findByCriteria(configurationSearch),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(ConsoleWebConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(ConsoleWebConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, ConfigurationSearch configurationSearch) {
		
		configurationSearch.setSearchAll((String)requestParams.get("configurationSearch.searchAll"));
		configurationSearch.setId((String)requestParams.get("configurationSearch.id"));
		
		int page = ParamUtils.parseInt(requestParams.get(ConsoleWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(ConsoleWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_CONFIGURATION_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(ConsoleWebConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(ConsoleWebConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(ConsoleWebConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(ConsoleWebConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(ConsoleWebConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(ConsoleWebConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute("searchAll", configurationSearch.getSearchAll());
		redirectAttributes.addAttribute("id", configurationSearch.getId());

		return redirectAttributes;
	}

	@ModelAttribute(ConfigurationWebConstants.ModelAttribute.CREATE)
	public Configuration populateConfigurationCreate(HttpServletRequest request) throws Exception {
		return configurationFacade.create();
	}

	@ModelAttribute(ConfigurationWebConstants.ModelAttribute.UPDATE)
	public Configuration populateConfigurationForm(HttpServletRequest request) throws Exception {
		return configurationFacade.create();
	}

	@ModelAttribute(ConfigurationWebConstants.ModelAttribute.SEARCH)
	public ConfigurationSearch populateConfigurationSearch(HttpServletRequest request) {
		return new ConfigurationSearch();
	}

	@GetMapping(value = ConfigurationWebConstants.Path.CONFIGURATION)
	public String list(
			@ModelAttribute(ConfigurationWebConstants.ModelAttribute.SEARCH) ConfigurationSearch configurationSearch,
			@ModelAttribute(ConfigurationWebConstants.ModelAttribute.UPDATE) Configuration configurationUpdate,
			Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(ConsoleWebConstants.PAGINATION, getPagination(configurationSearch, model, requestParams));

		if (configurationUpdate.getUuid() != null) {

			Configuration existingConfiguration = configurationFacade.findOneDtoByUuid(configurationUpdate.getUuid());
			
			if (existingConfiguration != null) {
				
				List<Object[]> revisions = configurationFacade.findHistoryByUuid(configurationUpdate.getUuid(), null, null);
				model.addAttribute(ConsoleWebConstants.Model.REVISIONS, revisions);
				
				model.addAttribute(ConfigurationWebConstants.ModelAttribute.UPDATE, existingConfiguration);
			} else {
				configurationUpdate.setUuid(null);
				addErrorMessage(model, ConsoleWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_CONFIGURATION_LIST;
	}

	@PostMapping(value = ConfigurationWebConstants.Path.CONFIGURATION, params = "create")
	public RedirectView create(
			@ModelAttribute(ConfigurationWebConstants.ModelAttribute.SEARCH) ConfigurationSearch configurationSearch,
			@ModelAttribute(ConfigurationWebConstants.ModelAttribute.CREATE) Configuration configurationCreate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (configurationCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {
			try {
				configurationCreate = configurationFacade.createDto(configurationCreate);

				addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.SAVE_SUCCESS);
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

	@PostMapping(value = ConfigurationWebConstants.Path.CONFIGURATION, params = "update")
	public RedirectView update(
			@ModelAttribute(ConfigurationWebConstants.ModelAttribute.SEARCH) ConfigurationSearch configurationSearch,
			@ModelAttribute(ConfigurationWebConstants.ModelAttribute.UPDATE) Configuration configurationUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (configurationUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			try {
				configurationUpdate = configurationFacade.updateDto(configurationUpdate);

				addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.SAVE_SUCCESS);
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

	@PostMapping(value = ConfigurationWebConstants.Path.CONFIGURATION, params = "delete")
	public RedirectView delete(
			@ModelAttribute(ConfigurationWebConstants.ModelAttribute.SEARCH) ConfigurationSearch configurationSearch,
			@ModelAttribute(ConfigurationWebConstants.ModelAttribute.UPDATE) Configuration configurationUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			configurationFacade.delete(configurationUpdate.getUuid());

			addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(Configuration.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(ConfigurationWebConstants.ModelAttribute.UPDATE, configurationUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, configurationSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CONFIGURATION);
		return redirectView;

	}
}
