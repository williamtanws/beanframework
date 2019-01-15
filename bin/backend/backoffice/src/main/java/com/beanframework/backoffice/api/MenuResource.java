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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.MenuWebConstants;
import com.beanframework.backoffice.data.MenuDto;
import com.beanframework.backoffice.data.MenuFieldDto;
import com.beanframework.backoffice.data.TreeJson;
import com.beanframework.backoffice.data.TreeJsonState;
import com.beanframework.backoffice.facade.MenuFacade;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.menu.domain.Menu;

@RestController
public class MenuResource {

	@Autowired
	private MenuFacade menuFacade;

	@RequestMapping(MenuWebConstants.Path.Api.CHECKID)
	public String checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = (String) requestParams.get(BackofficeWebConstants.Param.ID);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Menu.ID, id);
		MenuDto data = menuFacade.findOneByProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return "true";
			}
		}

		return data != null ? "false" : "true";
	}

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
			if (menuField.getDynamicField().getLanguage().getId().equals(locale.toString())) {

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
		
		if(StringUtils.isNotBlank(menu.getName())) {
			return menu.getName();
		}

		return "[" + menu.getId() + "]";
	}
}