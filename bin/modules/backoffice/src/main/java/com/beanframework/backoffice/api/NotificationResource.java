package com.beanframework.backoffice.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.NotificationWebConstants;
import com.beanframework.backoffice.api.data.NotificationDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.utils.TimeUtil;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.data.NotificationDto;
import com.beanframework.core.facade.NotificationFacade;

@RestController
public class NotificationResource extends AbstractResource {
	
	public static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ssa");
	
	@Autowired
	private NotificationFacade notificationFacade;

	@RequestMapping(value = NotificationWebConstants.Path.Api.NOTIFICATION, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);

		Page<NotificationDto> pagination = notificationFacade.findPage(dataTableRequest);

		DataTableResponse<DataTableResponseData> dataTableResponse = new DataTableResponse<DataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(notificationFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (NotificationDto dto : pagination.getContent()) {

			NotificationDataTableResponseData data = new NotificationDataTableResponseData();
			data.setMessage(StringUtils.stripToEmpty(dto.getMessage()));
			data.setTimeAgo(StringUtils.stripToEmpty(TimeUtil.getTimeAgo(dto.getCreatedDate())));
			data.setType(StringUtils.stripToEmpty(dto.getType()));
			data.setCreatedDate(dateFormat.format(dto.getCreatedDate()));
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}
}