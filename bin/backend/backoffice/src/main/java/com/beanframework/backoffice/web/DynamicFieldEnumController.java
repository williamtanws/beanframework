package com.beanframework.backoffice.web;

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

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.DynamicFieldEnumWebConstants;
import com.beanframework.backoffice.data.DynamicFieldEnumDto;
import com.beanframework.backoffice.data.DynamicFieldEnumSearch;
import com.beanframework.backoffice.data.LanguageDto;
import com.beanframework.backoffice.facade.DynamicFieldEnumFacade;
import com.beanframework.backoffice.facade.LanguageFacade;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.ParamUtils;

@Controller
public class DynamicFieldEnumController extends AbstractController {

	@Autowired
	private DynamicFieldEnumFacade dynamicFieldEnumFacade;

	@Autowired
	private LanguageFacade languageFacade;

	@Value(DynamicFieldEnumWebConstants.Path.DYNAMICFIELDENUM)
	private String PATH_DYNAMICFieldENUM;

	@Value(DynamicFieldEnumWebConstants.View.LIST)
	private String VIEW_DYNAMICFieldENUM_LIST;

	@Value(DynamicFieldEnumWebConstants.LIST_SIZE)
	private int MODULE_DYNAMICFieldENUM_LIST_SIZE;

	private Page<DynamicFieldEnumDto> getPagination(DynamicFieldEnumSearch dynamicFieldEnumSearch, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_DYNAMICFieldENUM_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null : propertiesStr.split(BackofficeWebConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		if (properties == null) {
			properties = new String[1];
			properties[0] = DynamicFieldEnumDto.SORT;
			direction = Sort.Direction.ASC;
		}

		Page<DynamicFieldEnumDto> pagination = dynamicFieldEnumFacade.findPage(dynamicFieldEnumSearch, PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes, @RequestParam Map<String, Object> requestParams, DynamicFieldEnumSearch dynamicFieldEnumSearch) {

		dynamicFieldEnumSearch.setSearchAll((String) requestParams.get("dynamicFieldEnumSearch.searchAll"));
		dynamicFieldEnumSearch.setId((String) requestParams.get("dynamicFieldEnumSearch.id"));
		dynamicFieldEnumSearch.setName((String) requestParams.get("dynamicFieldEnumSearch.name"));

		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_DYNAMICFieldENUM_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute("searchAll", dynamicFieldEnumSearch.getSearchAll());
		redirectAttributes.addAttribute("id", dynamicFieldEnumSearch.getId());
		redirectAttributes.addAttribute("name", dynamicFieldEnumSearch.getName());

		return redirectAttributes;
	}

	@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.CREATE)
	public DynamicFieldEnumDto populateDynamicFieldEnumCreate(HttpServletRequest request) throws Exception {
		return new DynamicFieldEnumDto();
	}

	@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.UPDATE)
	public DynamicFieldEnumDto populateDynamicFieldEnumForm(HttpServletRequest request) throws Exception {
		return new DynamicFieldEnumDto();
	}

	@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.SEARCH)
	public DynamicFieldEnumSearch populateDynamicFieldEnumSearch(HttpServletRequest request) {
		return new DynamicFieldEnumSearch();
	}

	@GetMapping(value = DynamicFieldEnumWebConstants.Path.DYNAMICFIELDENUM)
	public String list(@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.SEARCH) DynamicFieldEnumSearch dynamicFieldEnumSearch,
			@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.UPDATE) DynamicFieldEnumDto dynamicFieldEnumUpdate, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(BackofficeWebConstants.PAGINATION, getPagination(dynamicFieldEnumSearch, model, requestParams));

		List<LanguageDto> languages = languageFacade.findAllDtoLanguages();
		model.addAttribute("languages", languages);

		if (dynamicFieldEnumUpdate.getUuid() != null) {

			DynamicFieldEnumDto existingDynamicFieldEnum = dynamicFieldEnumFacade.findOneByUuid(dynamicFieldEnumUpdate.getUuid());

			List<Object[]> revisions = dynamicFieldEnumFacade.findHistoryByUuid(dynamicFieldEnumUpdate.getUuid(), null, null);
			model.addAttribute(BackofficeWebConstants.Model.REVISIONS, revisions);

			if (existingDynamicFieldEnum != null) {
				model.addAttribute(DynamicFieldEnumWebConstants.ModelAttribute.UPDATE, existingDynamicFieldEnum);
			} else {
				dynamicFieldEnumUpdate.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_DYNAMICFieldENUM_LIST;
	}

	@PostMapping(value = DynamicFieldEnumWebConstants.Path.DYNAMICFIELDENUM, params = "create")
	public RedirectView create(@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.SEARCH) DynamicFieldEnumSearch dynamicFieldEnumSearch,
			@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.CREATE) DynamicFieldEnumDto dynamicFieldEnumCreate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (dynamicFieldEnumCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				dynamicFieldEnumCreate = dynamicFieldEnumFacade.create(dynamicFieldEnumCreate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldEnumDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldEnumDto.UUID, dynamicFieldEnumCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, dynamicFieldEnumSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFieldENUM);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldEnumWebConstants.Path.DYNAMICFIELDENUM, params = "update")
	public RedirectView update(@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.SEARCH) DynamicFieldEnumSearch dynamicFieldEnumSearch,
			@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.UPDATE) DynamicFieldEnumDto dynamicFieldEnumUpdate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (dynamicFieldEnumUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				dynamicFieldEnumUpdate = dynamicFieldEnumFacade.update(dynamicFieldEnumUpdate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldEnumDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldEnumDto.UUID, dynamicFieldEnumUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, dynamicFieldEnumSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFieldENUM);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldEnumWebConstants.Path.DYNAMICFIELDENUM, params = "delete")
	public RedirectView delete(@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.SEARCH) DynamicFieldEnumSearch dynamicFieldEnumSearch,
			@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.UPDATE) DynamicFieldEnumDto dynamicFieldEnumUpdate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			dynamicFieldEnumFacade.delete(dynamicFieldEnumUpdate.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(DynamicFieldEnumDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(DynamicFieldEnumDto.UUID, dynamicFieldEnumUpdate.getUuid());
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, dynamicFieldEnumSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFieldENUM);
		return redirectView;

	}
}
