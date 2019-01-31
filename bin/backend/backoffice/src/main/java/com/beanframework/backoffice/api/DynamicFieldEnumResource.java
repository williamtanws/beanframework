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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.DynamicFieldEnumWebConstants;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataResponse;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.data.DynamicFieldEnumDataResponse;
import com.beanframework.core.data.DynamicFieldEnumDto;
import com.beanframework.core.facade.DynamicFieldEnumFacade;
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;

@RestController
public class DynamicFieldEnumResource {

	@Autowired
	private DynamicFieldEnumFacade dynamicFieldEnumFacade;
	
	@Autowired
	private LocaleMessageService localeMessageService;

	@RequestMapping(DynamicFieldEnumWebConstants.Path.Api.CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(DynamicFieldEnum.ID, id);

		DynamicFieldEnumDto data = dynamicFieldEnumFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}

	@RequestMapping(value = DynamicFieldEnumWebConstants.Path.Api.PAGE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DynamicFieldEnumDataResponse> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest(request);

		Page<DynamicFieldEnumDto> pagination = dynamicFieldEnumFacade.findPage(dataTableRequest);

		DataTableResponse<DynamicFieldEnumDataResponse> dataTableResponse = new DataTableResponse<DynamicFieldEnumDataResponse>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(dynamicFieldEnumFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (DynamicFieldEnumDto dto : pagination.getContent()) {

			DynamicFieldEnumDataResponse data = new DynamicFieldEnumDataResponse();
			data.setUuid(dto.getUuid());
			data.setId(dto.getId());
			data.setName(dto.getName());
			data.setSort(dto.getSort());
			data.setEnumGroup(dto.getEnumGroup());
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = DynamicFieldEnumWebConstants.Path.Api.HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataResponse> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		List<Object[]> history = dynamicFieldEnumFacade.findHistory(dataTableRequest);

		DataTableResponse<HistoryDataResponse> dataTableResponse = new DataTableResponse<HistoryDataResponse>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(dynamicFieldEnumFacade.countHistory(dataTableRequest));
		dataTableResponse.setRecordsFiltered(history.size());

		for (Object[] object : history) {

			DynamicFieldEnumDto dto = (DynamicFieldEnumDto) object[0];
			DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) object[1];
			RevisionType eevisionType = (RevisionType) object[2];
			Set<String> propertiesChanged = (Set<String>) object[3];

			HistoryDataResponse data = new HistoryDataResponse();
			data.setEntity(dto);
			data.setRevisionId(String.valueOf(revisionEntity.getId()));
			data.setRevisionDate(new SimpleDateFormat("dd MMMM yyyy, hh:mma").format(revisionEntity.getRevisionDate()));
			data.setRevisionType(eevisionType.name());
			for (String property : propertiesChanged) {
				String localized = localeMessageService.getMessage("module.dynamicfieldenum." + property);
				data.getPropertiesChanged().add(property + "=" + localized);
			}
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}
}