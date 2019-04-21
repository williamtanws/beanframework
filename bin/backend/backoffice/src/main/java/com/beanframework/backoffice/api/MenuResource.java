package com.beanframework.backoffice.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.RevisionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.MenuWebConstants;
import com.beanframework.backoffice.data.MenuDataResponse;
import com.beanframework.backoffice.data.TreeJson;
import com.beanframework.backoffice.data.TreeJsonState;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataResponse;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.data.MenuFieldDto;
import com.beanframework.core.facade.MenuFacade;
import com.beanframework.core.facade.MenuFacade.MenuPreAuthorizeEnum;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.domain.RevisionsEntity;

@RestController
public class MenuResource {

	@Autowired
	private MenuFacade menuFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_READ)
	@RequestMapping(MenuWebConstants.Path.Api.CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = (String) requestParams.get(BackofficeWebConstants.Param.ID);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Menu.ID, id);
		MenuDto data = menuFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_READ)
	@RequestMapping(MenuWebConstants.Path.Api.TREE)
	public List<TreeJson> list(Model model, @RequestParam Map<String, Object> requestParams) throws BusinessException {
		String uuid = (String) requestParams.get(BackofficeWebConstants.Param.UUID);

		List<MenuDto> rootMenu = menuFacade.findMenuTree();
		List<TreeJson> data = new ArrayList<TreeJson>();

		for (MenuDto menu : rootMenu) {
			if (StringUtils.isNotBlank(uuid)) {
				data.add(convertToJson(menu, UUID.fromString(uuid)));
			} else {
				data.add(convertToJson(menu, null));
			}
		}

		return data;
	}

	public TreeJson convertToJson(MenuDto menu, UUID selectedUuid) {

		TreeJson parent = new TreeJson();

		parent.setId(menu.getUuid().toString());
		parent.setText(convertName(menu));
		parent.setIcon(menu.getIcon());

		if (selectedUuid != null && selectedUuid.equals(menu.getUuid())) {
			parent.setState(new TreeJsonState(true));
		} else {
			parent.setState(new TreeJsonState(false));
		}

		List<TreeJson> children = new ArrayList<TreeJson>();
		if (menu.getChilds() != null && menu.getChilds().isEmpty() == false) {

			for (MenuDto child : menu.getChilds()) {
				children.add(convertToJson(child, selectedUuid));
			}
		}
		parent.setChildren(children);

		return parent;
	}

	public String convertName(MenuDto menu) {

		Locale locale = LocaleContextHolder.getLocale();

		for (MenuFieldDto menuField : menu.getFields()) {
			if (menuField.getDynamicFieldSlot().getDynamicField().getLanguage() != null) {
				if (menuField.getDynamicFieldSlot().getDynamicField().getLanguage().getId().equals(locale.toString())) {

					String name = menuField.getValue();

					if (StringUtils.isBlank(name)) {
						name = "[" + menu.getId() + "]";
					}

					if (menu.getEnabled() == false) {
						name = "<span class=\"text-muted\">" + name + "</span>";
					}

					return name;
				}
			}
		}

		if (StringUtils.isNotBlank(menu.getName())) {
			return menu.getName();
		}

		return "[" + menu.getId() + "]";
	}

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = MenuWebConstants.Path.Api.PAGE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		
		Page<MenuDto> pagination = menuFacade.findPage(dataTableRequest);

		DataTableResponse<DataTableResponseData> dataTableResponse = new DataTableResponse<DataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(menuFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (MenuDto dto : pagination.getContent()) {

			MenuDataResponse data = new MenuDataResponse();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			data.setSort(dto.getSort());
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_READ)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = MenuWebConstants.Path.Api.HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataResponse> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		List<Object[]> history = menuFacade.findHistory(dataTableRequest);

		DataTableResponse<HistoryDataResponse> dataTableResponse = new DataTableResponse<HistoryDataResponse>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(menuFacade.countHistory(dataTableRequest));
		dataTableResponse.setRecordsFiltered(history.size());

		for (Object[] object : history) {

			MenuDto dto = (MenuDto) object[0];
			RevisionsEntity revisionEntity = (RevisionsEntity) object[1];
			RevisionType revisionType = (RevisionType) object[2];
			Set<String> propertiesChanged = (Set<String>) object[3];

			HistoryDataResponse data = new HistoryDataResponse();
			data.setEntity(dto);
			data.setRevisionId(String.valueOf(revisionEntity.getId()));
			data.setRevisionDate(new SimpleDateFormat("dd MMMM yyyy, hh:mma").format(revisionEntity.getRevisionDate()));
			data.setRevisionType(localeMessageService.getMessage("revision."+revisionType.name()));
			for (String property : propertiesChanged) {
				String localized = localeMessageService.getMessage("module.menu." + property);
				data.getPropertiesChanged().add(property + "=" + localized);
			}

			dataTableResponse.getData().add(data);

		}
		return dataTableResponse;
	}
}