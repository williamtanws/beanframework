package com.beanframework.backoffice.api;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebCronjobConstants;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.service.CronjobFacade;

@RestController
public class CronjobResource {
	@Autowired
	private CronjobFacade cronjobFacade;

	@PreAuthorize(WebCronjobConstants.PreAuthorize.READ)
	@RequestMapping(WebCronjobConstants.Path.Api.CHECKID)
	public String checkId(Model model, @RequestParam Map<String, Object> requestParams) {

		String id = requestParams.get(WebBackofficeConstants.Param.ID).toString();
		Cronjob cronjob = cronjobFacade.findById(id);

		String uuidStr = (String) requestParams.get(WebBackofficeConstants.Param.UUID);
		if (StringUtils.isNotEmpty(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (cronjob != null && cronjob.getUuid().equals(uuid)) {
				return "true";
			}
		}

		return cronjob != null ? "false" : "true";
	}

	@PreAuthorize(WebCronjobConstants.PreAuthorize.READ)
	@RequestMapping(WebCronjobConstants.Path.Api.CHECKJOBGROUPNAME)
	public String checkName(Model model, @RequestParam Map<String, Object> requestParams) {

		String uuidStr = (String) requestParams.get(WebBackofficeConstants.Param.UUID);
		String jobGroup = (String) requestParams.get("jobGroup");
		String jobName = (String) requestParams.get("jobName");
		Cronjob cronjob = cronjobFacade.findByJobGroupAndJobName(jobGroup, jobName);

		if (StringUtils.isNotEmpty(uuidStr) && cronjob != null && cronjob.getUuid().equals(UUID.fromString(uuidStr))) {
			return "true";
		} else if (cronjob == null) {
			return "true";
		} else {
			return "false";
		}
	}

	@PreAuthorize(WebCronjobConstants.PreAuthorize.READ)
	@RequestMapping(WebCronjobConstants.Path.Api.CHECKJOBCLASS)
	public String checkJobClass(Model model, @RequestParam Map<String, Object> requestParams) {

		String jobClass = (String) requestParams.get("jobClass");

		try {
			Class.forName(jobClass).newInstance();
		} catch (Exception e) {
			return "false";
		}

		return "true";
	}

	@PreAuthorize(WebCronjobConstants.PreAuthorize.READ)
	@RequestMapping(WebCronjobConstants.Path.Api.CHECKCRONEXPRESSION)
	public String checkConExpression(Model model, @RequestParam Map<String, Object> requestParams) {

		String conExpression = (String) requestParams.get("conExpression");

		if(StringUtils.isNotEmpty(conExpression)) {
			boolean isValid = CronExpression.isValidExpression(conExpression);
			return isValid ? "true" : "false";
		}
		else {
			return "true";
		}
	}
}