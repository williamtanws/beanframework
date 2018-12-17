package com.beanframework.backoffice.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebMenuConstants;
import com.beanframework.backoffice.domain.TreeJson;
import com.beanframework.backoffice.domain.TreeJsonState;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuField;
import com.beanframework.menu.service.MenuFacade;

@RestController
public class MenuResource {
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private MenuFacade menuFacade;

	@PreAuthorize(WebMenuConstants.PreAuthorize.READ)
	@RequestMapping(WebMenuConstants.Path.Api.CHECKID)
	public String checkId(Model model, @RequestParam Map<String, Object> requestParams) {
		
		String id = (String) requestParams.get(WebBackofficeConstants.Param.ID);
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Menu.ID, id);
		Menu menu = modelService.findOneDtoByProperties(properties, Menu.class);

		String uuidStr = (String) requestParams.get(WebBackofficeConstants.Param.UUID);
		if (StringUtils.isNotEmpty(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (menu != null && menu.getUuid().equals(uuid)) {
				return "true";
			}
		}

		return menu != null ? "false" : "true";
	}

	@RequestMapping(WebMenuConstants.Path.Api.TREE)
	public List<TreeJson> list(Model model, @RequestParam Map<String, Object> requestParams) {
		String uuid = (String) requestParams.get(WebBackofficeConstants.Param.UUID);

		List<Menu> rootMenu = menuFacade.findMenuTree();
		List<TreeJson> data = new ArrayList<TreeJson>();

		for (Menu menu : rootMenu) {
			if (StringUtils.isNotEmpty(uuid)) {
				data.add(convertToJson(menu, UUID.fromString(uuid)));
			}
			else {
				data.add(convertToJson(menu, null));
			}
		}

		return data;
	}

	public TreeJson convertToJson(Menu menu, UUID selectedUuid) {

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
		if (menu.getChilds().isEmpty() == false) {

			for (Menu child : menu.getChilds()) {
				children.add(convertToJson(child, selectedUuid));
			}
		}
		parent.setChildren(children);

		return parent;
	}

	public String convertName(Menu menu) {

		Locale locale = LocaleContextHolder.getLocale();

		for (MenuField menuField : menu.getMenuFields()) {
			if (menuField.getId().equals(menu.getId()+"_name_"+locale.toString())) {

				String name = menuField.getValue();
				
				if (menu.getEnabled() == false) {
					name = "<span class=\"text-muted\">"+name + "</span>";
				}

				return name;
			}
		}

		return "[Unknown]";
	}
}