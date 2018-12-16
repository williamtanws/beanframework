package com.beanframework.backoffice.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebCustomerConstants;
import com.beanframework.common.service.ModelService;
import com.beanframework.customer.domain.Customer;

@RestController
public class CustomerResource {
	@Autowired
	private ModelService modelService;

	@PreAuthorize(WebCustomerConstants.PreAuthorize.READ)
	@RequestMapping(WebCustomerConstants.Path.Api.CHECKID)
	public String checkId(Model model, @RequestParam Map<String, Object> requestParams) {

		String id = requestParams.get(WebBackofficeConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Customer.ID, id);

		Customer customer = modelService.findOneDtoByProperties(properties, Customer.class);

		String uuidStr = (String) requestParams.get(WebBackofficeConstants.Param.UUID);
		if (StringUtils.isNotEmpty(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (customer != null && customer.getUuid().equals(uuid)) {
				return "true";
			}
		}

		return customer != null ? "false" : "true";
	}
}