package com.beanframework.backoffice.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.NotificationWebConstants;
import com.beanframework.backoffice.api.data.NotificationDataTableResponseData;
import com.beanframework.backoffice.api.data.NotificationMessageDataTableResponseData;
import com.beanframework.backoffice.api.data.NotificationOverallDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.TimeUtil;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.data.NotificationDto;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.facade.NotificationFacade;
import com.beanframework.core.facade.UserFacade;

@RestController
public class NotificationResource extends AbstractResource {

	public static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ssa");

	@Autowired
	private NotificationFacade notificationFacade;

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Autowired
	private Environment env;

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
			data.setType(StringUtils.stripToEmpty(dto.getType()));
			data.setCreatedDate(dateFormat.format(dto.getCreatedDate()));
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@RequestMapping(value = NotificationWebConstants.Path.Api.NOTIFICATION_CHECKED, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public void checkedNotification(HttpServletRequest request) throws Exception {
		notificationFacade.checkedNotification(userFacade.getCurrentUser().getUuid());
	}

	@RequestMapping(value = NotificationWebConstants.Path.Api.NOTIFICATION_NEW, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public NotificationOverallDataTableResponseData newNotification(HttpServletRequest request) throws Exception {
		
		UserDto currentUser = userFacade.getCurrentUser();
		
		if(currentUser.getUuid() == null) {
			return null;
		}

		List<NotificationDto> result = notificationFacade.findAllNewNotificationByUser(currentUser.getUuid());		
		NotificationOverallDataTableResponseData data = new NotificationOverallDataTableResponseData();

		if (result == null || result.isEmpty()) {
			String overallMessage = localeMessageService.getMessage("module.notification.overallmessage", new String[] { String.valueOf(0), "" });
			data.setTotal(0);
			data.setOverallMessage(overallMessage);
			return data;
		}

		List<NotificationMessageDataTableResponseData> notificationMessages = new ArrayList<NotificationMessageDataTableResponseData>();

		Set<String> types = getAllNotificationTypes(result);

		for (String type : types) {
			NotificationMessageDataTableResponseData notificationMessage = getNotificationMessageByType(type, result);
			if (notificationMessage != null) {
				notificationMessages.add(notificationMessage);
			}
		}

		int total = getTotalNotificationMessages(notificationMessages);
		String plural = total > 1 ? "s" : "";
		String overallMessage = localeMessageService.getMessage("module.notification.overallmessage", new String[] { String.valueOf(total), plural });
		data.setTotal(total);
		data.setOverallMessage(overallMessage);
		data.setNotificationMessages(notificationMessages);

		return data;
	}

	private int getTotalNotificationMessages(List<NotificationMessageDataTableResponseData> notificationMessages) {
		int count = 0;

		for (NotificationMessageDataTableResponseData notificationMessageDataTableResponseData : notificationMessages) {
			count = count + notificationMessageDataTableResponseData.getTotal();
		}

		return count;
	}

	private NotificationMessageDataTableResponseData getNotificationMessageByType(String type, List<NotificationDto> result) {
		String icon = env.getProperty("module.notification.icon." + type);
		String localemessagecode = env.getProperty("module.notification.localemessagecode." + type);
		if (icon == null) {
			return null;
		}

		int total = getTotalNotificationByType(type, result);
		if (total == 0) {
			return null;
		}

		Date oldestDate = getOldestCreatedDateNotificationByType(type, result);
		String timeAgo = TimeUtil.getTimeAgo(oldestDate);
		String plural = total > 1 ? "s" : "";
		String message = localeMessageService.getMessage(localemessagecode, new String[] { String.valueOf(total), plural });

		NotificationMessageDataTableResponseData data = new NotificationMessageDataTableResponseData();
		data.setIcon(icon);
		data.setMessage(message);
		data.setTimeAgo(timeAgo);
		data.setTotal(total);
		return data;
	}

	private int getTotalNotificationByType(String type, List<NotificationDto> result) {
		int count = 0;

		for (NotificationDto notificationDto : result) {
			if (notificationDto.getType().equals(type)) {
				count++;
			}
		}

		return count;
	}

	private Date getOldestCreatedDateNotificationByType(String type, List<NotificationDto> result) {
		Date oldestDate = null;

		for (NotificationDto notificationDto : result) {
			if (notificationDto.getType().equals(type)) {
				if (oldestDate == null) {
					oldestDate = notificationDto.getCreatedDate();
				} else {
					if (notificationDto.getCreatedDate().before(oldestDate)) {
						oldestDate = notificationDto.getCreatedDate();
					}
				}
			}
		}

		return oldestDate;
	}

	private Set<String> getAllNotificationTypes(List<NotificationDto> result) {
		Set<String> types = new HashSet<String>();

		for (NotificationDto notificationDto : result) {
			types.add(notificationDto.getType());
		}

		return types;
	}
}