package com.beanframework.cronjob.service;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;

@Component
public class CronjobFacadeImpl implements CronjobFacade {

	@Autowired
	private CronjobService cronjobService;
	
	@Autowired
	private CronjobManagerService cronjobManagerService;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Override
	public Cronjob create() {
		return cronjobService.create();
	}

	@Override
	public Cronjob initDefaults(Cronjob cronjob) {
		return cronjobService.initDefaults(cronjob);
	}

	@Override
	public void trigger(Cronjob cronjob, Errors bindingResult) {
		cronjobManagerService.trigger(cronjob);
	}

	@Override
	public Cronjob save(Cronjob cronjob, Errors bindingResult) {

		if (cronjob.getUuid() == null) {
			if (StringUtils.isEmpty(cronjob.getJobGroup())) {
				bindingResult.reject(Cronjob.JOB_GROUP,
						localeMessageService.getMessage(CronjobConstants.Locale.CRONJOB_GROUP_REQUIRED));
				return cronjob;
			}

			if (StringUtils.isEmpty(cronjob.getJobName())) {
				bindingResult.reject(Cronjob.JOB_NAME,
						localeMessageService.getMessage(CronjobConstants.Locale.CRONJOB_NAME_REQUIRED));
				return cronjob;
			}

			if (cronjobService.isGroupAndNameExists(cronjob.getJobGroup(), cronjob.getJobName())) {
				bindingResult.reject(Cronjob.JOB_GROUP,
						localeMessageService.getMessage(CronjobConstants.Locale.CRONJOB_NAME_GROUP_EXISTS));
				return cronjob;
			}
		}

		return cronjobService.save(cronjob);
	}

	@Override
	public Cronjob addCronjobData(UUID uuid, String name, String value, Errors bindingResult) {

		Cronjob updateCronjob = cronjobService.findByUuid(uuid);

		List<CronjobData> datas = updateCronjob.getCronjobDatas();

		for (int i = 0; i < datas.size(); i++) {
			if (datas.get(i).getName().equals(name)) {
				bindingResult.rejectValue(CronjobData.NAME,
						localeMessageService.getMessage(CronjobConstants.Locale.CRONJOB_DATA_NAME_EXISTS));
			}
		}

		CronjobData data = new CronjobData();
		data.setName(name);
		data.setValue(value);
		data.setCronjob(updateCronjob);

		updateCronjob.getCronjobDatas().add(data);

		return cronjobService.save(updateCronjob);
	}

	@Override
	public void removeCronjobData(UUID uuid) throws Exception {

		cronjobService.deleteCronJobData(uuid);
	}

	@Override
	public Cronjob delete(UUID uuid, Errors bindingResult) {
		Cronjob cronjob = findByUuid(uuid);
		try {
			cronjobManagerService.deleteJobByUuid(cronjob.getUuid());
			cronjobService.deleteByUuid(cronjob.getUuid());
		} catch (SchedulerException e) {
			bindingResult.reject(Cronjob.UUID, e.getMessage());
		}
		return cronjob;
	}

	@Override
	public Cronjob deleteCronjobByGroupAndName(String jobGroup, String jobName, Errors bindingResult) {
		Cronjob cronjob = findByJobGroupAndJobName(jobGroup, jobName);
		try {
			cronjobManagerService.deleteJobByUuid(cronjob.getUuid());
			cronjobService.deleteByUuid(cronjob.getUuid());
		} catch (SchedulerException e) {
			bindingResult.rejectValue(Cronjob.UUID, e.getMessage());
		}
		return cronjob;
	}

	@Override
	public void deleteAll() {
		cronjobService.deleteAll();
	}

	@Override
	public boolean isGroupAndNameExists(String jobGroup, String jobName) {
		return cronjobService.isGroupAndNameExists(jobGroup, jobName);
	}

	@Override
	public Cronjob findByUuid(UUID uuid) {
		return cronjobService.findByUuid(uuid);
	}

	@Override
	public Cronjob findById(String id) {
		return cronjobService.findById(id);
	}

	@Override
	public Cronjob findByJobGroupAndJobName(String jobGroup, String jobName) {
		return cronjobService.findByJobGroupAndJobName(jobGroup, jobName);
	}

	@Override
	public Page<Cronjob> page(Cronjob cronjob, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		return cronjobService.page(cronjob, pageRequest);
	}
}
