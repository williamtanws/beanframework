package com.beanframework.backoffice.api;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.beanframework.backoffice.VendorWebConstants;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.DataTableResponseData;
import com.beanframework.common.data.HistoryDataResponse;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.data.VendorDto;
import com.beanframework.core.facade.VendorFacade;
import com.beanframework.vendor.domain.Vendor;
import com.beanframework.user.domain.RevisionsEntity;

@RestController
public class VendorResource {
	@Autowired
	private VendorFacade vendorFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@RequestMapping(VendorWebConstants.Path.Api.CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Vendor.ID, id);

		VendorDto data = vendorFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}

	@RequestMapping(value = VendorWebConstants.Path.Api.PAGE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest(request);

		Page<VendorDto> pagination = vendorFacade.findPage(dataTableRequest);

		DataTableResponse<DataTableResponseData> dataTableResponse = new DataTableResponse<DataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(vendorFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (VendorDto dto : pagination.getContent()) {

			DataTableResponseData data = new DataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = VendorWebConstants.Path.Api.HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataResponse> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		List<Object[]> history = vendorFacade.findHistory(dataTableRequest);

		DataTableResponse<HistoryDataResponse> dataTableResponse = new DataTableResponse<HistoryDataResponse>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(vendorFacade.countHistory(dataTableRequest));
		dataTableResponse.setRecordsFiltered(history.size());

		for (Object[] object : history) {

			VendorDto dto = (VendorDto) object[0];
			RevisionsEntity revisionEntity = (RevisionsEntity) object[1];
			RevisionType eevisionType = (RevisionType) object[2];
			Set<String> propertiesChanged = (Set<String>) object[3];

			HistoryDataResponse data = new HistoryDataResponse();
			data.setEntity(dto);
			data.setRevisionId(String.valueOf(revisionEntity.getId()));
			data.setRevisionDate(new SimpleDateFormat("dd MMMM yyyy, hh:mma").format(revisionEntity.getRevisionDate()));
			data.setRevisionType(eevisionType.name());
			for (String property : propertiesChanged) {
				String localized = localeMessageService.getMessage("module.vendor." + property);
				data.getPropertiesChanged().add(property + "=" + localized);
			}

			dataTableResponse.getData().add(data);

		}
		return dataTableResponse;
	}
}