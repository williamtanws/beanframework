package com.beanframework.backoffice.api;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.CronjobWebConstants;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.DataTableResponseData;
import com.beanframework.common.data.HistoryDataResponse;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.core.facade.CronjobFacade;
import com.beanframework.cronjob.domain.Cronjob;

@RestController
public class CronjobResource {

	@Autowired
	private CronjobFacade cronjobFacade;
	
	@Autowired
	private LocaleMessageService localeMessageService;

	@RequestMapping(CronjobWebConstants.Path.Api.CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Cronjob.ID, id);

		CronjobDto data = cronjobFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}
	
	@RequestMapping(value = CronjobWebConstants.Path.Api.PAGE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest(request);

		Page<CronjobDto> pagination = cronjobFacade.findPage(dataTableRequest);

		DataTableResponse<DataTableResponseData> dataTableResponse = new DataTableResponse<DataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(cronjobFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (CronjobDto dto : pagination.getContent()) {

			DataTableResponseData data = new DataTableResponseData();
			data.setUuid(dto.getUuid());
			data.setId(dto.getId());
			data.setName(dto.getName());
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = CronjobWebConstants.Path.Api.HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataResponse> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		List<Object[]> history = cronjobFacade.findHistory(dataTableRequest);

		DataTableResponse<HistoryDataResponse> dataTableResponse = new DataTableResponse<HistoryDataResponse>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(cronjobFacade.countHistory(dataTableRequest));
		dataTableResponse.setRecordsFiltered(history.size());

		for (Object[] object : history) {

			CronjobDto dto = (CronjobDto) object[0];
			DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) object[1];
			RevisionType eevisionType = (RevisionType) object[2];
			Set<String> propertiesChanged = (Set<String>) object[3];

			HistoryDataResponse data = new HistoryDataResponse();
			data.setEntity(dto);
			data.setRevisionId(String.valueOf(revisionEntity.getId()));
			data.setRevisionDate(new SimpleDateFormat("dd MMMM yyyy, hh:mma").format(revisionEntity.getRevisionDate()));
			data.setRevisionType(eevisionType.name());
			for (String property : propertiesChanged) {
				String localized = localeMessageService.getMessage("module.cronjob." + property);
				data.getPropertiesChanged().add(property + "=" + localized);
			}

			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@RequestMapping(CronjobWebConstants.Path.Api.CHECKJOBGROUPNAME)
	public boolean checkName(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		String jobGroup = (String) requestParams.get("jobGroup");
		String name = (String) requestParams.get("name");

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Cronjob.JOB_GROUP, jobGroup);
		properties.put(Cronjob.NAME, name);

		CronjobDto cronjob = cronjobFacade.findOneProperties(properties);

		if (StringUtils.isNotBlank(uuidStr) && cronjob != null && cronjob.getUuid().equals(UUID.fromString(uuidStr))) {
			return true;
		} else if (cronjob == null) {
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping(CronjobWebConstants.Path.Api.CHECKJOBCLASS)
	public boolean checkJobClass(Model model, @RequestParam Map<String, Object> requestParams) {

		String jobClass = (String) requestParams.get("jobClass");

		try {
			Class.forName(jobClass).newInstance();
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@RequestMapping(CronjobWebConstants.Path.Api.CHECKCRONEXPRESSION)
	public boolean checkConExpression(Model model, @RequestParam Map<String, Object> requestParams) {

		String conExpression = (String) requestParams.get("conExpression");

		if (StringUtils.isNotBlank(conExpression)) {
			boolean isValid = CronExpression.isValidExpression(conExpression);
			return isValid;
		} else {
			return true;
		}
	}
}