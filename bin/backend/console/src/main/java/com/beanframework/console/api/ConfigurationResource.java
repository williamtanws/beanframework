package com.beanframework.console.api;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.DataTableResponseData;
import com.beanframework.common.data.HistoryDataResponse;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.console.ConfigurationWebConstants;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.console.data.ConfigurationDataResponse;
import com.beanframework.core.data.ConfigurationDto;
import com.beanframework.core.facade.ConfigurationFacade;

@RestController
public class ConfigurationResource {

	@Autowired
	private ConfigurationFacade configurationFacade;

	@GetMapping(ConfigurationWebConstants.Path.Api.CHECKID)
	public boolean checkIdExists(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(ConsoleWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, id);

		ConfigurationDto configuration = configurationFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(ConsoleWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (configuration != null && configuration.getUuid().equals(uuid)) {
				return true;
			}
		}

		return configuration != null ? false : true;
	}

	@RequestMapping(value = ConfigurationWebConstants.Path.Api.PAGE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest<ConfigurationDto> dataTableRequest = new DataTableRequest<ConfigurationDto>(request);

		Page<ConfigurationDto> pagination = configurationFacade.findPage(dataTableRequest);

		DataTableResponse<DataTableResponseData> dataTableResponse = new DataTableResponse<DataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(configurationFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (ConfigurationDto dto : pagination.getContent()) {

			ConfigurationDataResponse data = new ConfigurationDataResponse();
			data.setUuid(dto.getUuid());
			data.setId(dto.getId());
			data.setValue(dto.getValue());
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = ConfigurationWebConstants.Path.Api.HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataResponse> history(HttpServletRequest request) throws Exception {

		DataTableRequest<Object[]> dataTableRequest = new DataTableRequest<Object[]>(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		List<Object[]> history = configurationFacade.findHistory(dataTableRequest);

		DataTableResponse<HistoryDataResponse> dataTableResponse = new DataTableResponse<HistoryDataResponse>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(configurationFacade.countHistory(dataTableRequest));
		dataTableResponse.setRecordsFiltered(history.size());

		for (Object[] object : history) {

			ConfigurationDto dto = (ConfigurationDto) object[0];
			DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) object[1];
			RevisionType eevisionType = (RevisionType) object[2];
			Set<String> propertiesChanged = (Set<String>) object[3];

			HistoryDataResponse data = new HistoryDataResponse();
			data.setEntity(dto);
			data.setRevisionId(String.valueOf(revisionEntity.getId()));
			data.setRevisionDate(new SimpleDateFormat("dd MMMM yyyy, hh:mma").format(revisionEntity.getRevisionDate()));
			data.setRevisionType(eevisionType.name());
			data.setPropertiesChanged(propertiesChanged);

			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}
}