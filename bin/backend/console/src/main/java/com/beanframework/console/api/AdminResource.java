package com.beanframework.console.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.console.AdminWebConstants;
import com.beanframework.console.AdminWebConstants.AdminPreAuthorizeEnum;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.console.data.AdminDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.AdminDto;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.facade.AdminFacade;
import com.beanframework.user.domain.Admin;

@RestController
public class AdminResource extends AbstractResource {

	@Autowired
	private AdminFacade adminFacade;

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_READ)
	@GetMapping(AdminWebConstants.Path.Api.CHECKID)
	public boolean checkIdExists(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(ConsoleWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Admin.ID, id);
		AdminDto admin = adminFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(ConsoleWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (admin != null && admin.getUuid().equals(uuid)) {
				return true;
			}
		}

		return admin != null ? false : true;
	}

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = AdminWebConstants.Path.Api.PAGE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);

		Page<AdminDto> pagination = adminFacade.findPage(dataTableRequest);

		DataTableResponse<DataTableResponseData> dataTableResponse = new DataTableResponse<DataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(adminFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (AdminDto dto : pagination.getContent()) {

			AdminDataTableResponseData data = new AdminDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = AdminWebConstants.Path.Api.HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		return historyDataTableResponse(dataTableRequest, adminFacade.findHistory(dataTableRequest), adminFacade.countHistory(dataTableRequest));
	}
}