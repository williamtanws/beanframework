package com.beanframework.backoffice.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.MyAccountWebConstants;
import com.beanframework.backoffice.data.UserDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.facade.UserFacade;

@RestController
public class UserResource extends AbstractResource {
	
	@Autowired
	private UserFacade userFacade;

	@RequestMapping(value = MyAccountWebConstants.Path.MYACCOUNT, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);

		Page<UserDto> pagination = userFacade.findPage(dataTableRequest);

		DataTableResponse<DataTableResponseData> dataTableResponse = new DataTableResponse<DataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(userFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (UserDto dto : pagination.getContent()) {

			UserDataTableResponseData data = new UserDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}
}